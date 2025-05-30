package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.file.FileDTO;
import com.tup.ps.erpevents.dtos.file.FilePostDTO;
import com.tup.ps.erpevents.dtos.file.FilePutDTO;
import com.tup.ps.erpevents.dtos.notification.KeyValueCustomPair;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.enums.BatchFileType;
import com.tup.ps.erpevents.repositories.ClientRepository;
import com.tup.ps.erpevents.repositories.EmployeeRepository;
import com.tup.ps.erpevents.repositories.FileRepository;
import com.tup.ps.erpevents.repositories.SupplierRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.FileService;
import com.tup.ps.erpevents.services.MinioService;
import com.tup.ps.erpevents.services.NotificationService;
import io.minio.GetObjectResponse;
import io.minio.errors.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String[] FILE_FIELDS = {
            "fileName", "fileContentType", "reviewNote"
    };

    @Value("${app.url}")
    private String appUrl;

    public static final int UUID_LENGTH = 8;

    @Autowired
    private FileRepository fileRepository;
    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MinioService minioService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private GenericSpecification<FileEntity> specification;

    @Override
    public Page<FileDTO> findAll(Pageable pageable) {
        return fileRepository.findAll(pageable)
                .map(file -> modelMapper.map(file, FileDTO.class));
    }

    @Override
    public Optional<FileDTO> getById(Long id) {
        return fileRepository.findById(id)
                .map(file -> modelMapper.map(file, FileDTO.class));
    }


    @Override
    public void delete(Long id) {
        Optional<FileEntity> entity = fileRepository.findById(id);
        if (entity.isPresent()) {
            entity.get().setSoftDelete(true);
            fileRepository.save(entity.get());
        } else {
            throw new EntityNotFoundException("File with id " + id + " not found");
        }
    }


    @Override
    public Page<FileDTO> findByFilters(Pageable pageable,
                                       String fileType,
                                       Boolean softDelete,
                                       String searchValue,
                                       LocalDate creationStart,
                                       LocalDate creationEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (fileType != null) {
            try {
                BatchFileType parsedType = BatchFileType.valueOf(fileType.toUpperCase());
                filters.put("fileType", parsedType);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de archivo inválido");
            }
        }

        if (softDelete != null) {
            filters.put("softDelete", softDelete);
        }

        Specification<FileEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null && !searchValue.isBlank()) {
            Specification<FileEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, FILE_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationStart == null) ^ (creationEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationStart != null && creationEnd != null) {
            if (creationStart.isAfter(creationEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationStart no puede ser mayor a creationEnd");
            }
            Specification<FileEntity> dateSpec = specification.filterBetween(
                    creationStart.atStartOfDay(), creationEnd.atTime(23, 59, 59), "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return fileRepository.findAll(spec, pageable)
                .map(file -> modelMapper.map(file, FileDTO.class));
    }


    @Override
    @Transactional
    public FileDTO save(FilePostDTO dto, MultipartFile file) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {

        FileEntity fileEntity = modelMapper.map(dto, FileEntity.class);

        FileEntity savedEntity = fileRepository.save(fileEntity);
        fileEntity.setFileUrl(generateFileUrl(savedEntity.getIdFile()));

        savedEntity.setFileName(
                savedEntity.getFileType().name().toLowerCase(Locale.ENGLISH)
                        + "-"
                        + UUID.randomUUID().toString().substring(0, UUID_LENGTH)
                        + "." + getFileExtension(Objects.requireNonNull(file.getOriginalFilename()))
        );
        fileRepository.save(savedEntity);

        String prefix = getPrefix(savedEntity);
        minioService.saveFile(savedEntity.getFileName(), prefix, file);

        NotificationPostDTO notification = buildNotification(savedEntity);
        notificationService.sendEmailToContacts(notification);

        return modelMapper.map(savedEntity, FileDTO.class);
    }

    @Override
    @Transactional
    public FileDTO update(Long id, FilePutDTO dto, MultipartFile file) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {

        FileEntity fileEntity = fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado"));

        modelMapper.map(dto, fileEntity);
        fileEntity.setUpdateDate(LocalDateTime.now());

        FileEntity updatedEntity = fileRepository.save(fileEntity);

        String prefix = getPrefix(updatedEntity);
        minioService.saveFile(updatedEntity.getFileName(), prefix, file);

        NotificationPostDTO notification = buildNotification(updatedEntity);
        notificationService.sendEmailToContacts(notification);

        return modelMapper.map(updatedEntity, FileDTO.class);
    }

    @Override
    @Transactional
    public GetObjectResponse getFileStreamById(Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new EntityNotFoundException("No file with id: " + fileId + " exists"));

        String objectPath = getPrefix(fileEntity) + "/" + fileEntity.getFileName();

        return minioService.getFile(objectPath);
    }

    @Override
    public GetObjectResponse getBySupplierId(Long supplierId, Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        SupplierEntity supplierEntity = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        FileEntity fileEntity = fileRepository.findByIdFileAndSupplier(fileId, supplierEntity)
                .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado"));

        String objectPath = getPrefix(fileEntity) + "/" + fileEntity.getFileName();

        return minioService.getFile(objectPath);
    }

    @Override
    public GetObjectResponse getByClientId(Long clientId, Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        ClientEntity clientEntity = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        FileEntity fileEntity = fileRepository.findByIdFileAndClient(fileId, clientEntity)
                .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado"));

        String objectPath = getPrefix(fileEntity) + "/" + fileEntity.getFileName();

        return minioService.getFile(objectPath);
    }

    @Override
    public GetObjectResponse getByEmployeeId(Long employeeId, Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        FileEntity fileEntity = fileRepository.findByIdFileAndEmployee(fileId, employeeEntity)
                .orElseThrow(() -> new EntityNotFoundException("Archivo no encontrado"));

        String objectPath = getPrefix(fileEntity) + "/" + fileEntity.getFileName();

        return minioService.getFile(objectPath);
    }

    private String generateFileUrl(Long fileId) {
        return appUrl + "/event-files/" + fileId;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    private String getPrefix(FileEntity fileEntity) {
        if (fileEntity.getSupplier() != null) return "supplier-" + fileEntity.getSupplier().getIdSupplier();
        if (fileEntity.getClient() != null) return "client-" + fileEntity.getClient().getIdClient();
        if (fileEntity.getEmployee() != null) return "employee-" + fileEntity.getEmployee().getIdEmployee();
        return "general";
    }

    private NotificationPostDTO buildNotification(FileEntity fileEntity) {
        List<Long> contactIds = new ArrayList<>();
        if (fileEntity.getSupplier() != null) contactIds.add(fileEntity.getSupplier().getIdSupplier());
        if (fileEntity.getClient() != null) contactIds.add(fileEntity.getClient().getIdClient());
        if (fileEntity.getEmployee() != null) contactIds.add(fileEntity.getEmployee().getIdEmployee());

        List<KeyValueCustomPair> variables = new ArrayList<>();
        variables.add(new KeyValueCustomPair("fileName", fileEntity.getFileName()));
        variables.add(new KeyValueCustomPair("fileType", fileEntity.getFileType().name()));
        variables.add(new KeyValueCustomPair("date", fileEntity.getUpdateDate().toString()));

        return new NotificationPostDTO(
                4L,
                contactIds,
                "Nuevo archivo disponible: " + fileEntity.getFileName(),
                variables,
                "FILE_UPLOAD"
        );
    }


}
