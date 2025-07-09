package com.tup.ps.erpevents.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.event.account.AccountDTO;
import com.tup.ps.erpevents.services.AccountService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    private AccountDTO accountDTO;

    @BeforeEach
    void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setIdAccount(1L);
        accountDTO.setBalance(new BigDecimal("1000.00"));
        accountDTO.setSoftDelete(false);
        accountDTO.setCreationDate(LocalDateTime.now().minusDays(5));
        accountDTO.setUpdateDate(LocalDateTime.now());
    }

    @Test
    @DisplayName("AC-001/Should return all accounts successfully")
    void testGetAllAccountsSuccess() throws Exception {
        Page<AccountDTO> page = new PageImpl<>(List.of(accountDTO));
        given(accountService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/accounts")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].idAccount").value(1));
    }

    @Test
    @DisplayName("AC-002/Should return account by ID successfully")
    void testGetAccountByIdSuccess() throws Exception {
        given(accountService.findById(1L)).willReturn(Optional.of(accountDTO));

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAccount").value(1));
    }

    @Test
    @DisplayName("AC-003/Should return 404 when account is not found")
    void testGetAccountByIdNotFound() throws Exception {
        given(accountService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Cuenta no encontrada"));
    }

    @Test
    @DisplayName("AC-004/Should create account successfully")
    void testCreateAccountSuccess() throws Exception {
        given(accountService.save(any(AccountDTO.class))).willReturn(accountDTO);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAccount").value(1));
    }

    @Test
    @DisplayName("AC-005/Should update account successfully")
    void testUpdateAccountSuccess() throws Exception {
        given(accountService.update(eq(1L), any(AccountDTO.class))).willReturn(accountDTO);

        mockMvc.perform(put("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAccount").value(1));
    }

    @Test
    @DisplayName("AC-006/Should delete account successfully")
    void testDeleteAccountSuccess() throws Exception {
        mockMvc.perform(delete("/accounts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("AC-007/Should return accounts by filters successfully")
    void testFilterAccountsSuccess() throws Exception {
        Page<AccountDTO> page = new PageImpl<>(List.of(accountDTO));
        given(accountService.findByFilters(any(), eq(true), eq("test"), any(), any())).willReturn(page);

        mockMvc.perform(get("/accounts/filter")
                        .param("isActive", "true")
                        .param("searchValue", "test")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idAccount").value(1));
    }

    @Test
    @DisplayName("AC-008/Should return account by event ID successfully")
    void testFindByEventIdSuccess() throws Exception {
        given(accountService.findByEventId(any(), eq(1L))).willReturn(accountDTO);

        mockMvc.perform(get("/accounts/event/1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAccount").value(1));
    }
}
