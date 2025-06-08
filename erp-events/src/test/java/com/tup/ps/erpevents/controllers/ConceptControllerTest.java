package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPostDTO;
import com.tup.ps.erpevents.dtos.event.account.concept.ConceptPutDTO;
import com.tup.ps.erpevents.enums.AccountingConcept;
import com.tup.ps.erpevents.services.ConceptService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class ConceptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConceptService conceptService;

    private ConceptDTO concept;
    private ConceptPostDTO conceptPost;
    private ConceptPutDTO conceptPut;

    @BeforeEach
    void setUp() {
        concept = new ConceptDTO();
        concept.setIdConcept(1L);
        concept.setIdAccount(10L);
        concept.setAccountingDate(LocalDateTime.of(2023, 6, 1, 12, 0));
        concept.setConcept(AccountingConcept.CATERING);
        concept.setComments("Pago recibido");
        concept.setAmount(BigDecimal.valueOf(1500.0));
        concept.setIdFile(100L);
        concept.setFileContentType("application/pdf");
        concept.setCreationDate(LocalDateTime.now());
        concept.setUpdateDate(LocalDateTime.now());
        concept.setSoftDelete(false);

        conceptPost = new ConceptPostDTO(10L, LocalDateTime.of(2023, 6, 1, 12, 0), AccountingConcept.CATERING,
                "Pago recibido", BigDecimal.valueOf(1500.0), 100L, "application/pdf");

        conceptPut = new ConceptPutDTO(1L, 10L, LocalDateTime.of(2023, 6, 1, 12, 0), AccountingConcept.CATERING,
                "Pago actualizado", BigDecimal.valueOf(1800.0), 100L, "application/pdf");
    }

    @Test
    @DisplayName("CC-001/Should return all concepts successfully")
    void testGetAllConceptsSuccess() throws Exception {
        Page<ConceptDTO> page = new PageImpl<>(List.of(concept));
        given(conceptService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/concepts")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idConcept").value(1L));
    }

    @Test
    @DisplayName("CC-002/Should return concept by ID successfully")
    void testGetConceptByIdSuccess() throws Exception {
        given(conceptService.findById(1L)).willReturn(Optional.of(concept));

        mockMvc.perform(get("/concepts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idConcept").value(1L));
    }

    @Test
    @DisplayName("CC-003/Should return 404 when concept not found")
    void testGetConceptByIdNotFound() throws Exception {
        given(conceptService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/concepts/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Concepto no encontrado"));
    }

    @Test
    @DisplayName("CC-004/Should create concept successfully")
    void testCreateConceptSuccess() throws Exception {
        given(conceptService.save(any(ConceptPostDTO.class))).willReturn(concept);

        mockMvc.perform(post("/concepts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conceptPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idConcept").value(1L));
    }

    @Test
    @DisplayName("CC-005/Should return 400 for invalid concept post")
    void testCreateConceptBadRequest() throws Exception {
        mockMvc.perform(post("/concepts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\": 100}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CC-006/Should update concept successfully")
    void testUpdateConceptSuccess() throws Exception {
        given(conceptService.update(eq(1L), any(ConceptPutDTO.class))).willReturn(concept);

        mockMvc.perform(put("/concepts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(conceptPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idConcept").value(1L));
    }

    @Test
    @DisplayName("CC-007/Should delete concept successfully")
    void testDeleteConceptSuccess() throws Exception {
        mockMvc.perform(delete("/concepts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("CC-008/Should return concepts by filters successfully")
    void testFilterConceptsSuccess() throws Exception {
        Page<ConceptDTO> page = new PageImpl<>(List.of(concept));
        given(conceptService.findByFilters(any(), eq(AccountingConcept.CATERING), eq(10L), eq("Pago"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/concepts/filter")
                        .param("concept", "CATERING")
                        .param("idAccount", "10")
                        .param("searchValue", "Pago")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idConcept").value(1L));
    }

    @Test
    @DisplayName("CC-009/Should return concepts by account ID")
    void testFindByAccountId() throws Exception {
        Page<ConceptDTO> page = new PageImpl<>(List.of(concept));
        given(conceptService.findByAccountId(any(), eq(10L))).willReturn(page);

        mockMvc.perform(get("/concepts/account/10")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idConcept").value(1L));
    }
}

