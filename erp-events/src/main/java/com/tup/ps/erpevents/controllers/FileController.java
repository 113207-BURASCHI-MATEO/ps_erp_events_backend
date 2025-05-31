package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.file.FileDTO;
import com.tup.ps.erpevents.dtos.file.FilePostDTO;
import com.tup.ps.erpevents.dtos.file.FilePutDTO;
import com.tup.ps.erpevents.services.FileService;
import io.minio.GetObjectResponse;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import static org.springframework.util.FileCopyUtils.BUFFER_SIZE;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
@Validated
@Tag(name = "Gestión de Archivos", description = "Operaciones relacionadas con archivos cargados")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Obtener todos los archivos paginados")
    @GetMapping
    public ResponseEntity<Page<FileDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(value = "sort", defaultValue = "creationDate") String sortProperty,
                                                @RequestParam(value = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(fileService.findAll(pageable));
    }

    @Operation(summary = "Filtrar archivos por parámetros")
    @GetMapping("/filter")
    public ResponseEntity<Page<FileDTO>> filterFiles(@RequestParam(required = false) String fileType,
                                                     @RequestParam(required = false) Boolean softDelete,
                                                     @RequestParam(required = false) String searchValue,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creationStart,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creationEnd,
                                                     Pageable pageable) {
        return ResponseEntity.ok(fileService.findByFilters(pageable, fileType, softDelete, searchValue, creationStart, creationEnd));
    }

    @Operation(summary = "Obtener archivo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getById(@PathVariable Long id) {
        return fileService.getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Archivo no encontrado"));
    }

    @Operation(summary = "Obtener archivo y metadata por ID")
    @GetMapping("/file/{fileId}")
    public ResponseEntity<StreamingResponseBody> getFile(@PathVariable(name = "fileId") Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse response = fileService.getFileStreamById(fileId);
        return getStreamingResponseBodyResponseEntity(response);
    }


    @Operation(summary = "Guardar un nuevo archivo")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileDTO> save(
            @RequestPart("data") FilePostDTO dto,
            @RequestPart("file") MultipartFile file) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fileService.save(dto, file));
    }

    @Operation(summary = "Actualizar un archivo existente")
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileDTO> update(
            @PathVariable Long id,
            @RequestPart("data") FilePutDTO dto,
            @RequestPart("file") MultipartFile file) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException {

        return ResponseEntity.ok(fileService.update(id, dto, file));
    }

    @Operation(summary = "Eliminar un archivo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fileService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener archivo y metadata por ID de archivo y proveedor")
    @GetMapping("/supplier/{supplierId}/file/{fileId}")
    public ResponseEntity<StreamingResponseBody> getFileBySupplier(
            @PathVariable Long supplierId,
            @PathVariable Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse response = fileService.getBySupplierId(supplierId, fileId);
        return getStreamingResponseBodyResponseEntity(response);
    }

    @Operation(summary = "Obtener archivo y metadata por ID de archivo y cliente")
    @GetMapping("/client/{clientId}/file/{fileId}")
    public ResponseEntity<StreamingResponseBody> getFileByClient(
            @PathVariable Long clientId,
            @PathVariable Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse response = fileService.getByClientId(clientId, fileId);
        return getStreamingResponseBodyResponseEntity(response);
    }

    @Operation(summary = "Obtener archivo y metadata por ID de archivo y empleado")
    @GetMapping("/employee/{employeeId}/file/{fileId}")
    public ResponseEntity<StreamingResponseBody> getFileByEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long fileId) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse response = fileService.getByEmployeeId(employeeId, fileId);
        return getStreamingResponseBodyResponseEntity(response);
    }

    @NotNull
    private ResponseEntity<StreamingResponseBody> getStreamingResponseBodyResponseEntity(GetObjectResponse response) {
        StreamingResponseBody responseBody = outputStream -> {
            try (response) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = response.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        };
        return ResponseEntity.ok(responseBody);
    }

}

