package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.file.FileDTO;
import com.tup.ps.erpevents.dtos.file.FilePostDTO;
import com.tup.ps.erpevents.dtos.file.FilePutDTO;
import io.minio.GetObjectResponse;
import io.minio.errors.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface FileService {

    Page<FileDTO> findAll(Pageable pageable);

    Optional<FileDTO> getById(Long id);

    GetObjectResponse getBySupplierId(Long supplierId, Long fileId)
            throws IOException, ServerException, InsufficientDataException,
            ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException;

    GetObjectResponse getByClientId(Long clientId, Long fileId)
            throws IOException, ServerException, InsufficientDataException,
            ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException;

    GetObjectResponse getByEmployeeId(Long employeeId, Long fileId)
            throws IOException, ServerException, InsufficientDataException,
            ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException;

    GetObjectResponse getFileStreamById(Long fileId)
            throws IOException, ServerException, InsufficientDataException,
            ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidResponseException, XmlParserException, InternalException;

    FileDTO save(FilePostDTO dto, MultipartFile file) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException;

    FileDTO update(Long id, FilePutDTO dto, MultipartFile file) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException,
            XmlParserException, InternalException;

    void delete(Long id);


    Page<FileDTO> findByFilters(Pageable pageable,
                                String fileType,
                                Boolean softDelete,
                                String searchValue,
                                LocalDate creationStart,
                                LocalDate creationEnd);
}

