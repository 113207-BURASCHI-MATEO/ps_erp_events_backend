package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.payment.PaymentDTO;
import com.tup.ps.erpevents.dtos.payment.PaymentPostDTO;
import com.tup.ps.erpevents.enums.*;
import com.tup.ps.erpevents.services.PaymentService;
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
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    private PaymentDTO payment;
    private PaymentPostDTO paymentPost;

    @BeforeEach
    void setUp() {
        payment = new PaymentDTO();
        payment.setIdPayment(1L);
        payment.setPaymentDate(LocalDateTime.of(2025, 5, 23, 10, 0));
        payment.setIdClient(1L);
        payment.setAmount(15000.0);
        payment.setDetail("Pago por evento corporativo");
        payment.setStatus(PaymentStatus.PAID);
        payment.setReviewNote("http://mp.com");
        payment.setCreationDate(LocalDateTime.now());
        payment.setUpdateDate(LocalDateTime.now());
        payment.setSoftDelete(false);

        paymentPost = new PaymentPostDTO();
        paymentPost.setPaymentDate(LocalDateTime.of(2025, 5, 23, 10, 0));
        paymentPost.setIdClient(1L);
        paymentPost.setAmount(BigDecimal.valueOf(15000.0));
        paymentPost.setDetail("Pago por evento corporativo");
        paymentPost.setStatus(PaymentStatus.PAID);
    }

    @Test
    @DisplayName("PC-001/Should return all payments successfully")
    void testGetAllPaymentsSuccess() throws Exception {
        Page<PaymentDTO> page = new PageImpl<>(List.of(payment));
        given(paymentService.findAll(any(Pageable.class), eq(true))).willReturn(page);

        mockMvc.perform(get("/payments")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idPayment").value(1));
    }

    @Test
    @DisplayName("PC-002/Should return payment by ID successfully")
    void testGetPaymentByIdSuccess() throws Exception {
        given(paymentService.findById(1L)).willReturn(Optional.of(payment));

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idClient").value(1));
    }

    @Test
    @DisplayName("PC-003/Should return 404 when payment is not found by ID")
    void testGetPaymentByIdNotFound() throws Exception {
        given(paymentService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Pago no encontrado"));
    }

    @Test
    @DisplayName("PC-004/Should create payment successfully")
    void testCreatePaymentSuccess() throws Exception {
        given(paymentService.save(any(PaymentPostDTO.class))).willReturn(payment);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idClient").value(1));
    }

    @Test
    @DisplayName("PC-005/Should update payment status successfully")
    void testUpdatePaymentStatus() throws Exception {
        given(paymentService.updateStatus(1L, PaymentStatus.PAID)).willReturn(payment);

        mockMvc.perform(put("/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PaymentStatus.PAID)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    @DisplayName("PC-006/Should filter payments successfully")
    void testFilterPaymentsSuccess() throws Exception {
        Page<PaymentDTO> page = new PageImpl<>(List.of(payment));
        given(paymentService.findByFilters(any(), eq(PaymentStatus.PAID), eq("event"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/payments/filter")
                        .param("status", "PAID")
                        .param("searchValue", "event")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idClient").value(1));
    }

    @Test
    @DisplayName("PC-007/Should receive MercadoPago notification successfully")
    void testReceiveNotification() throws Exception {
        Map<String, Object> body = Map.of("topic", "payment", "resource", "/v1/payments/123");

        mockMvc.perform(post("/payments/notification/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isNoContent());
    }
}

