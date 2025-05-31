package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.employee.EmployeeDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePostDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePutDTO;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDTO employee;
    private EmployeePostDTO employeePost;
    private EmployeePutDTO employeePut;

    @BeforeEach
    void setUp() {
        employee = new EmployeeDTO();
        employee.setIdEmployee(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@mail.com");
        employee.setPhoneNumber("1234567890");
        employee.setDocumentType(DocumentType.DNI);
        employee.setDocumentNumber("12345678");
        employee.setAliasCbu("alias-john-doe");
        employee.setSoftDelete(false);
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setCuit("20312345678");
        employee.setPosition("Organizador");
        employee.setHireDate(LocalDate.of(2023, 1, 1));

        employeePost = new EmployeePostDTO();
        employeePost.setFirstName("John");
        employeePost.setLastName("Doe");
        employeePost.setEmail("john.doe@mail.com");
        employeePost.setPhoneNumber("1234567890");
        employeePost.setDocumentType(DocumentType.DNI);
        employeePost.setDocumentNumber("12345678");
        employeePost.setAliasCbu("alias-john-doe");
        employeePost.setBirthDate(LocalDate.of(1990, 1, 1));
        employeePost.setCuit("20312345678");
        employeePost.setPosition("Organizador");
        employeePost.setHireDate(LocalDate.of(2023, 1, 1));
        employeePost.setPassword("securepassword");

        employeePut = new EmployeePutDTO();
        employeePut.setIdEmployee(1L);
        employeePut.setFirstName("John");
        employeePut.setLastName("Doe");
        employeePut.setEmail("john.doe@mail.com");
        employeePut.setPhoneNumber("1234567890");
        employeePut.setDocumentType(DocumentType.DNI);
        employeePut.setDocumentNumber("12345678");
        employeePut.setAliasCbu("alias-john-doe");
        employeePut.setBirthDate(LocalDate.of(1990, 1, 1));
        employeePut.setCuit("20312345678");
        employeePut.setPosition("Organizador");
        employeePut.setHireDate(LocalDate.of(2023, 1, 1));
    }

    @Test
    @DisplayName("EC-001/Should return all employees successfully")
    void testGetAllEmployeesSuccess() throws Exception {
        Page<EmployeeDTO> page = new PageImpl<>(List.of(employee));
        given(employeeService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/employees")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("John"));
    }

    @Test
    @DisplayName("EC-002/Should return employee by ID successfully")
    void testGetEmployeeByIdSuccess() throws Exception {
        given(employeeService.findById(1L)).willReturn(Optional.of(employee));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("EC-003/Should return 404 when employee is not found by ID")
    void testGetEmployeeByIdNotFound() throws Exception {
        given(employeeService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Empleado no encontrado"));
    }

    @Test
    @DisplayName("EC-004/Should create employee successfully")
    void testCreateEmployeeSuccess() throws Exception {
        given(employeeService.save(any(EmployeePostDTO.class))).willReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeePost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("EC-005/Should return 400 when invalid employee post request")
    void testCreateEmployeeBadRequest() throws Exception {
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("EC-006/Should update employee successfully")
    void testUpdateEmployeeSuccess() throws Exception {
        given(employeeService.update(eq(1L), any(EmployeePutDTO.class))).willReturn(employee);

        mockMvc.perform(put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeePut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    @DisplayName("EC-007/Should delete employee successfully")
    void testDeleteEmployeeSuccess() throws Exception {
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("EC-008/Should return employees by filters successfully")
    void testFilterEmployeesSuccess() throws Exception {
        Page<EmployeeDTO> page = new PageImpl<>(List.of(employee));
        given(employeeService.findByFilters(any(), eq("DNI"), eq(true), eq("Doe"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/employees/filter")
                        .param("documentType", "DNI")
                        .param("isActive", "true")
                        .param("searchValue", "Doe")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("John"));
    }
}
