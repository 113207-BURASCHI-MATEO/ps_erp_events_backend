package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.file.FileDTO;
import com.tup.ps.erpevents.dtos.file.FilePostDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.enums.BatchFileType;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.FileServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private FileRepository fileRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private MinioService minioService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private GenericSpecification<FileEntity> specification;

    private FileEntity fileEntity;
    private FileDTO fileDTO;
    private FilePostDTO filePostDTO;

    @BeforeEach
    void setup() {
        fileEntity = new FileEntity();
        fileEntity.setIdFile(1L);
        fileEntity.setFileName("test-file.pdf");
        fileEntity.setFileType(BatchFileType.PAYMENT);
        fileEntity.setUpdateDate(LocalDateTime.now());

        fileDTO = new FileDTO();
        fileDTO.setIdFile(1L);

        filePostDTO = new FilePostDTO();
        filePostDTO.setFileType(String.valueOf(BatchFileType.PAYMENT));
    }

    @Test
    @DisplayName("FS-001/Should return paginated list of FileDTO")
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<FileEntity> page = new PageImpl<>(List.of(fileEntity));
        given(fileRepository.findAll(pageable)).willReturn(page);
        given(modelMapper.map(any(FileEntity.class), eq(FileDTO.class))).willReturn(fileDTO);

        Page<FileDTO> result = fileService.findAll(pageable);

        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("FS-002/Should get file by ID")
    void testGetById() {
        given(fileRepository.findById(1L)).willReturn(Optional.of(fileEntity));
        given(modelMapper.map(any(FileEntity.class), eq(FileDTO.class))).willReturn(fileDTO);

        Optional<FileDTO> result = fileService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdFile());
    }

    @Test
    @DisplayName("FS-003/Should delete file by ID")
    void testDeleteFile() {
        given(fileRepository.findById(1L)).willReturn(Optional.of(fileEntity));

        fileService.delete(1L);

        assertTrue(fileEntity.getSoftDelete());
        verify(fileRepository).save(fileEntity);
    }

    @Test
    @DisplayName("FS-004/Should throw EntityNotFoundException on delete")
    void testDeleteFileNotFound() {
        given(fileRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> fileService.delete(1L));
    }

    @Test
    @DisplayName("FS-005/Should throw error if only one creation date is provided")
    void testFindByFiltersInvalidDateRange() {
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(ResponseStatusException.class, () ->
                fileService.findByFilters(pageable, null, null, null, LocalDate.now(), null));
    }

    @Test
    @DisplayName("FS-006/Should throw error if startDate is after endDate")
    void testFindByFiltersDateOrderInvalid() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDate start = LocalDate.of(2023, 12, 10);
        LocalDate end = LocalDate.of(2023, 11, 10);

        assertThrows(ResponseStatusException.class, () ->
                fileService.findByFilters(pageable, null, null, null, start, end));
    }

    // Add more tests as needed (e.g., for save, update, getFileStreamById, etc.)
}
