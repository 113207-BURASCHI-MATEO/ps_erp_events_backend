package com.tup.ps.erpevents.controllers;

import com.tup.ps.erpevents.dtos.commons.ErrorApi;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Global exception handler for the application, responsible for catching
 * and handling exceptions thrown by controllers.
 */
@ControllerAdvice
@Hidden
@AllArgsConstructor
public class ControllerExceptionHandler {

    private static final Integer BYTES_TO_MB_DIVISOR = 10000;

    private static final Integer MB_DECIMALS_DIVISOR = 100;

    private static final Integer BYTE_TOLERANCE = 100000;


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorApi> handleError(Exception exception) {
        ErrorApi error = buildError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorApi> handleError(HttpClientErrorException exception) {
        ErrorApi error = buildError(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApi> handleError(MethodArgumentNotValidException exception) {
        StringBuilder message = new StringBuilder("Validation failed: ");
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            message.append(String.format("Field '%s' %s. ", error.getField(), error.getDefaultMessage()));
        });
        ErrorApi error = buildError(message.toString(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorApi> handleError(ResponseStatusException exception) {
        ErrorApi error = buildError(exception.getReason(), HttpStatus.valueOf(exception.getStatusCode().value()));
        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ErrorApi> handleFileSizeLimitExceededException(FileSizeLimitExceededException exception) {
        double actualSize = Math.floor((double) exception.getActualSize() / BYTES_TO_MB_DIVISOR) / MB_DECIMALS_DIVISOR;
        double permittedSize = Math.floor(
                ((double) exception.getPermittedSize() - BYTE_TOLERANCE) / BYTES_TO_MB_DIVISOR
        ) / MB_DECIMALS_DIVISOR;
        String message = "File size of '" + exception.getFileName()
                + "', cannot be greater than "
                + permittedSize
                + "MB. Actual size is "
                + actualSize + "MB";
        ErrorApi error = buildError(message, HttpStatus.PAYLOAD_TOO_LARGE);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ErrorApi> handleSizeLimitExceededException(SizeLimitExceededException exception) {
        double actualSize = Math.floor((double) exception.getActualSize() / BYTES_TO_MB_DIVISOR) / MB_DECIMALS_DIVISOR;
        double permittedSize = Math.floor(
                ((double) exception.getPermittedSize() - BYTE_TOLERANCE) / BYTES_TO_MB_DIVISOR
        ) / MB_DECIMALS_DIVISOR;
        String message = "Total request size cannot be greater than "
                + permittedSize
                + "MB. Actual size is "
                + actualSize + "MB";
        ErrorApi error = buildError(message, HttpStatus.PAYLOAD_TOO_LARGE);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorApi> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof IllegalStateException) {
            Throwable innerCause = ((IllegalStateException) cause).getCause();
            if (innerCause instanceof FileSizeLimitExceededException) {
                return handleFileSizeLimitExceededException((FileSizeLimitExceededException) innerCause);
            } else if (innerCause instanceof SizeLimitExceededException) {
                return handleSizeLimitExceededException((SizeLimitExceededException) innerCause);
            }
        }
        ErrorApi error = buildError("Maximum upload size exceeded", HttpStatus.PAYLOAD_TOO_LARGE);
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorApi> handleError(EntityNotFoundException exception) {
        ErrorApi error = buildError("Not found - The element required was not found", HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private ErrorApi buildError(String message, HttpStatus status) {
        return ErrorApi.builder()
                .timestamp(String.valueOf(Timestamp.from(ZonedDateTime.now().toInstant())))
                .error(status.getReasonPhrase())
                .status(status.value())
                .message(message)
                .build();
    }
}