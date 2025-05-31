package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.client.ClientPutDTO;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.services.ClientService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    private ClientDTO client;
    private ClientPostDTO clientPost;
    private ClientPutDTO clientPut;

    @BeforeEach
    void setUp() {
        client = new ClientDTO();
        client.setIdClient(1L);
        client.setFirstName("Alice");
        client.setLastName("Smith");
        client.setDocumentType(DocumentType.DNI);
        client.setDocumentNumber("12345678");
        client.setSoftDelete(true);
        client.setPhoneNumber("1234567890");
        client.setAliasCbu("alias-cbu-prueba");
        client.setSoftDelete(true);
        client.setEmail("alice@mail.com");

        clientPost = new ClientPostDTO();
        clientPost.setFirstName("Alice");
        clientPost.setLastName("Smith");
        clientPost.setDocumentType(DocumentType.DNI);
        clientPost.setDocumentNumber("12345678");
        clientPost.setPhoneNumber("1234567890");
        clientPost.setAliasCbu("alias-cbu-prueba");
        clientPost.setEmail("alice@mail.com");


        clientPut = new ClientPutDTO();
        clientPut.setIdClient(1L);
        clientPut.setFirstName("Alice");
        clientPut.setLastName("Smith");
        clientPut.setDocumentType(DocumentType.DNI);
        clientPut.setDocumentNumber("12345678");
        clientPut.setPhoneNumber("1234567890");
        clientPut.setAliasCbu("alias-cbu-prueba");
        clientPut.setEmail("alice@mail.com");
    }

    @Test
    @DisplayName("CC-001/Should return all clients successfully")
    void testGetAllClientsSuccess() throws Exception {
        Page<ClientDTO> page = new PageImpl<>(List.of(client));
        given(clientService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/clients")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Alice"));
    }

    @Test
    @DisplayName("CC-002/Should return client by ID successfully")
    void testGetClientByIdSuccess() throws Exception {
        given(clientService.findById(1L)).willReturn(Optional.of(client));

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    @DisplayName("CC-003/Should return 404 when client is not found by ID")
    void testGetClientByIdNotFound() throws Exception {
        given(clientService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado"));
    }

    @Test
    @DisplayName("CC-004/Should create client successfully")
    void testCreateClientSuccess() throws Exception {
        given(clientService.save(any(ClientPostDTO.class))).willReturn(client);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    @DisplayName("CC-005/Should return 400 when invalid client post request")
    void testCreateClientBadRequest() throws Exception {
        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CC-006/Should update client successfully")
    void testUpdateClientSuccess() throws Exception {
        given(clientService.update(eq(1L), any(ClientPutDTO.class))).willReturn(client);

        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    @DisplayName("CC-007/Should delete client successfully")
    void testDeleteClientSuccess() throws Exception {
        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("CC-008/Should return clients by filters successfully")
    void testFilterClientsSuccess() throws Exception {
        Page<ClientDTO> page = new PageImpl<>(List.of(client));
        given(clientService.findByFilters(any(), eq("DNI"), eq(true), eq("Smith"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/clients/filter")
                        .param("documentType", "DNI")
                        .param("isActive", "true")
                        .param("searchValue", "Smith")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Alice"));
    }

    @Test
    @DisplayName("CC-009/Should return client by document number successfully")
    void testFindByDocumentSuccess() throws Exception {
        given(clientService.findByDocumentTypeAndDocumentNumber(DocumentType.DNI, "12345678"))
                .willReturn(Optional.of(client));

        mockMvc.perform(get("/clients/exists")
                        .param("documentType", "DNI")
                        .param("documentNumber", "12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    @DisplayName("CC-010/Should return 404 when client is not found by document")
    void testFindByDocumentNotFound() throws Exception {
        given(clientService.findByDocumentTypeAndDocumentNumber(DocumentType.DNI, "12345678"))
                .willReturn(Optional.empty());

        mockMvc.perform(get("/clients/exists")
                        .param("documentType", "DNI")
                        .param("documentNumber", "12345678"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Cliente no encontrado"));
    }
}

