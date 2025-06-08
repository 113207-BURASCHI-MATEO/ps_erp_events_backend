package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.supplier.SupplierDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPostDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPutDTO;
import com.tup.ps.erpevents.enums.*;
import com.tup.ps.erpevents.services.SupplierService;
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
public class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SupplierService supplierService;

    private SupplierDTO supplier;
    private SupplierPostDTO supplierPost;
    private SupplierPutDTO supplierPut;

    @BeforeEach
    void setUp() {
        supplier = new SupplierDTO();
        supplier.setIdSupplier(1L);
        supplier.setName("Eventos XYZ");
        supplier.setCuit("20304567891");
        supplier.setEmail("proveedor@xyz.com");
        supplier.setPhoneNumber("3511234567");
        supplier.setAliasCbu("eventos.xyz.cbu");
        supplier.setSupplierType(SupplierType.CATERING);
        supplier.setAddress("Av. Siempre Viva 123");
        supplier.setSoftDelete(false);

        supplierPost = new SupplierPostDTO();
        supplierPost.setName("Eventos XYZ");
        supplierPost.setCuit("20304567891");
        supplierPost.setEmail("proveedor@xyz.com");
        supplierPost.setPhoneNumber("3511234567");
        supplierPost.setAliasCbu("eventos.xyz.cbu");
        supplierPost.setSupplierType(SupplierType.CATERING);
        supplierPost.setAddress("Av. Siempre Viva 123");

        supplierPut = new SupplierPutDTO();
        supplierPut.setIdSupplier(1L);
        supplierPut.setName("Eventos XYZ");
        supplierPut.setCuit("20304567891");
        supplierPut.setEmail("proveedor@xyz.com");
        supplierPut.setPhoneNumber("3511234567");
        supplierPut.setAliasCbu("eventos.xyz.cbu");
        supplierPut.setSupplierType(SupplierType.CATERING);
        supplierPut.setAddress("Av. Siempre Viva 123");
    }

    @Test
    @DisplayName("SC-001/Should return all suppliers successfully")
    void testGetAllSuppliersSuccess() throws Exception {
        Page<SupplierDTO> page = new PageImpl<>(List.of(supplier));
        given(supplierService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/suppliers")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Eventos XYZ"));
    }

    @Test
    @DisplayName("SC-002/Should return supplier by ID successfully")
    void testGetSupplierByIdSuccess() throws Exception {
        given(supplierService.findById(1L)).willReturn(Optional.of(supplier));

        mockMvc.perform(get("/suppliers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eventos XYZ"));
    }

    @Test
    @DisplayName("SC-003/Should return 404 when supplier is not found by ID")
    void testGetSupplierByIdNotFound() throws Exception {
        given(supplierService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/suppliers/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Proveedor no encontrado"));
    }

    @Test
    @DisplayName("SC-004/Should create supplier successfully")
    void testCreateSupplierSuccess() throws Exception {
        given(supplierService.save(any(SupplierPostDTO.class))).willReturn(supplier);

        mockMvc.perform(post("/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Eventos XYZ"));
    }

    @Test
    @DisplayName("SC-005/Should return 400 when invalid supplier post request")
    void testCreateSupplierBadRequest() throws Exception {
        mockMvc.perform(post("/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("SC-006/Should update supplier successfully")
    void testUpdateSupplierSuccess() throws Exception {
        given(supplierService.update(eq(1L), any(SupplierPutDTO.class))).willReturn(supplier);

        mockMvc.perform(put("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eventos XYZ"));
    }

    @Test
    @DisplayName("SC-007/Should delete supplier successfully")
    void testDeleteSupplierSuccess() throws Exception {
        mockMvc.perform(delete("/suppliers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("SC-008/Should return suppliers by filters successfully")
    void testFilterSuppliersSuccess() throws Exception {
        Page<SupplierDTO> page = new PageImpl<>(List.of(supplier));
        given(supplierService.findByFilters(any(), eq("CATERING"), eq(true), eq("XYZ"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/suppliers/filter")
                        .param("supplierType", "CATERING")
                        .param("isActive", "true")
                        .param("searchValue", "XYZ")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Eventos XYZ"));
    }
}
