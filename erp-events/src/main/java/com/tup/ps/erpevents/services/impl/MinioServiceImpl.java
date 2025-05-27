
package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.services.MinioService;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioServiceImpl implements MinioService {

  private final MinioClient minioClient;

  @Value("${minio.config.bucket-name}")
  private String bucketName;

  @Autowired
  public MinioServiceImpl(MinioClient minioClientParam) {
    this.minioClient = minioClientParam;
  }

  @Override
  public GetObjectResponse getFile(String fileName) throws IOException,
      ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

    GetObjectArgs getObjectArgs = GetObjectArgs.builder()
        .bucket(bucketName)
        .object(fileName)
        .build();

    return minioClient.getObject(getObjectArgs);
  }

  @Override
  public ObjectWriteResponse saveFile(String fileName, String filePrefix, MultipartFile file) throws IOException,
      ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

    PutObjectArgs putObjectArgs = PutObjectArgs.builder()
        .bucket(bucketName)
        .object(filePrefix + "/" + fileName)
        .contentType(file.getContentType())
        .stream(file.getInputStream(), file.getSize(), -1)
        .build();

    return minioClient.putObject(putObjectArgs);
  }

  @Override
  public void deleteFile(String fileName) throws IOException,
      ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

    RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
        .bucket(bucketName)
        .object(fileName)
        .build();


    minioClient.removeObject(removeObjectArgs);
  }

}
