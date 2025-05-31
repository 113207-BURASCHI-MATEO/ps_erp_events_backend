package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.employee.EmployeeDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePostDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePutDTO;
import com.tup.ps.erpevents.dtos.user.UserRegisterDTO;
import com.tup.ps.erpevents.entities.EmployeeEntity;
import com.tup.ps.erpevents.entities.UserEntity;
import com.tup.ps.erpevents.enums.RoleName;
import com.tup.ps.erpevents.repositories.EmployeeRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.EmployeeServiceImpl;
import com.tup.ps.erpevents.services.impl.UserServiceImpl;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private SecurityService securityService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private NotificationService notificationService;

    @Mock
    private GenericSpecification<EmployeeEntity> specification;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeEntity employeeEntity;
    private EmployeeDTO employeeDTO;
    private EmployeePostDTO employeePostDTO;
    private EmployeePutDTO employeePutDTO;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        employeeEntity = new EmployeeEntity();
        employeeEntity.setIdEmployee(1L);
        employeeEntity.setFirstName("Ana");
        employeeEntity.setLastName("Smith");
        employeeEntity.setSoftDelete(false);
        employeeEntity.setEmail("ana@mail.com");

        employeeDTO = new EmployeeDTO();
        employeeDTO.setIdEmployee(1L);
        employeeDTO.setFirstName("Ana");
        employeeDTO.setLastName("Smith");

        employeePostDTO = new EmployeePostDTO();
        employeePostDTO.setFirstName("Ana");
        employeePostDTO.setLastName("Smith");
        employeePostDTO.setEmail("ana@mail.com");

        employeePutDTO = new EmployeePutDTO();
        employeePutDTO.setFirstName("Updated");
        employeePutDTO.setLastName("Name");

        userEntity = new UserEntity();
        userEntity.setIdUser(1L);
        userEntity.setEmail("ana@mail.com");
        userEntity.setFirstName("Ana");

        employeeEntity.setUser(userEntity);
    }

    @Test
    @DisplayName("ES-001/Should return all employees")
    void testFindAll() {
        Page<EmployeeEntity> page = new PageImpl<>(List.of(employeeEntity));
        given(employeeRepository.findAllBySoftDelete(false, Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(EmployeeEntity.class), eq(EmployeeDTO.class))).willReturn(employeeDTO);

        Page<EmployeeDTO> result = employeeService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Ana", result.getContent().get(0).getFirstName());
    }

    @Test
    @DisplayName("ES-002/Should return employee by ID")
    void testFindById() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employeeEntity));
        given(modelMapper.map(any(EmployeeEntity.class), eq(EmployeeDTO.class))).willReturn(employeeDTO);

        Optional<EmployeeDTO> result = employeeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ana", result.get().getFirstName());
    }

    @Test
    @DisplayName("ES-003/Should save new employee")
    void testSaveEmployee() {
        given(modelMapper.map(employeePostDTO, UserRegisterDTO.class)).willReturn(new UserRegisterDTO());
        given(securityService.registerEntity(any(), eq(RoleName.EMPLOYEE))).willReturn(userEntity);
        given(modelMapper.map(employeePostDTO, EmployeeEntity.class)).willReturn(employeeEntity);
        given(employeeRepository.save(any(EmployeeEntity.class))).willReturn(employeeEntity);
        given(modelMapper.map(any(EmployeeEntity.class), eq(EmployeeDTO.class))).willReturn(employeeDTO);

        EmployeeDTO result = employeeService.save(employeePostDTO);

        assertNotNull(result);
        assertEquals("Ana", result.getFirstName());
    }

    @Test
    @DisplayName("ES-004/Should update employee")
    void testUpdateEmployee() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employeeEntity));
        doAnswer(invocation -> {
            EmployeePutDTO source = invocation.getArgument(0);
            EmployeeEntity target = invocation.getArgument(1);
            target.setFirstName(source.getFirstName());
            target.setLastName(source.getLastName());
            return null;
        }).when(modelMapper).map(any(EmployeePutDTO.class), any(EmployeeEntity.class));
        given(employeeRepository.save(any(EmployeeEntity.class))).willReturn(employeeEntity);
        given(modelMapper.map(any(EmployeeEntity.class), eq(EmployeeDTO.class))).willReturn(employeeDTO);

        EmployeeDTO result = employeeService.update(1L, employeePutDTO);

        assertNotNull(result);
        assertEquals("Ana", result.getFirstName());
    }

    @Test
    @DisplayName("ES-005/Should soft delete employee")
    void testDeleteEmployee() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employeeEntity));

        employeeService.delete(1L);

        assertTrue(employeeEntity.getSoftDelete());
        verify(userServiceImpl).softDeleteUser(1L, "ana@mail.com");
        verify(employeeRepository).save(employeeEntity);
    }

    @Test
    @DisplayName("ES-006/Should throw exception when employee not found for update")
    void testUpdateNotFound() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.update(1L, employeePutDTO));
    }

    @Test
    @DisplayName("ES-007/Should throw exception when employee not found for delete")
    void testDeleteNotFound() {
        given(employeeRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.delete(1L));
    }

    @Test
    @DisplayName("ES-008/Should throw when only one date is given")
    void testFindByFiltersOneDate() {
        Pageable pageable = PageRequest.of(0, 10);

        assertThrows(ResponseStatusException.class, () ->
                employeeService.findByFilters(pageable, null, true, null, LocalDate.now(), null));
    }

    @Test
    @DisplayName("ES-009/Should throw when birthdateStart > birthdateEnd")
    void testFindByFiltersInvalidDateRange() {
        Pageable pageable = PageRequest.of(0, 10);
        LocalDate start = LocalDate.now();
        LocalDate end = start.minusDays(1);

        assertThrows(ResponseStatusException.class, () ->
                employeeService.findByFilters(pageable, null, true, null, start, end));
    }

    @Test
    @DisplayName("ES-010/Should find by filters")
    void testFindByFiltersSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<EmployeeEntity> page = new PageImpl<>(List.of(employeeEntity));
        given(specification.dynamicFilter(anyMap())).willReturn((root, query, cb) -> null);
        given(employeeRepository.findAll(any(Specification.class), eq(pageable))).willReturn(page);
        given(modelMapper.map(any(EmployeeEntity.class), eq(EmployeeDTO.class))).willReturn(employeeDTO);

        Page<EmployeeDTO> result = employeeService.findByFilters(pageable, "DNI", true, "Ana", null, null);

        assertEquals(1, result.getTotalElements());
        assertEquals("Ana", result.getContent().get(0).getFirstName());
    }
}

