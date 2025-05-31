package com.tup.ps.erpevents.services;

import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderPayment;
import com.mercadopago.resources.preference.Preference;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.services.impl.MercadoPagoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class MercadoPagoServiceImplTest {

    @Mock private PreferenceClient preferenceClient;
    @Mock private MerchantOrderClient merchantOrderClient;
    @Mock private PaymentRefundClient paymentRefundClient;
    @InjectMocks private MercadoPagoServiceImpl mercadoPagoService;

    @Mock private ClientEntity clientEntity;
    @Mock private PaymentEntity paymentEntity;
    @Mock private MerchantOrder merchantOrder;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mercadoPagoService, "WEB_URL", "http://localhost:4200");
        ReflectionTestUtils.setField(mercadoPagoService, "BACK_URL", "http://localhost:8080");
        ReflectionTestUtils.setField(mercadoPagoService, "MP_TOKEN", "fake-token");
    }

    @Test
    @DisplayName("MP-001/Should create preference")
    void testCreatePreference() throws MPException, MPApiException {
        given(paymentEntity.getIdPayment()).willReturn(1L);
        given(paymentEntity.getAmount()).willReturn(1000.0);
        given(paymentEntity.getDetail()).willReturn("Servicio A");
        given(clientEntity.getIdClient()).willReturn(99L);
        given(paymentEntity.getClient()).willReturn(clientEntity);

        Preference mockPreference = mock(Preference.class);
        given(preferenceClient.create(any(PreferenceRequest.class))).willReturn(mockPreference);

        Preference result = mercadoPagoService.createPreference(paymentEntity);

        assertNotNull(result);
    }

    @Test
    @DisplayName("MP-002/Should process merchant order as paid")
    void testProcessMerchantOrderPaid() throws MPException, MPApiException {
        MerchantOrder mockOrder = mock(MerchantOrder.class);
        given(mockOrder.getOrderStatus()).willReturn("paid");
        given(merchantOrderClient.get(123L)).willReturn(mockOrder);

        boolean result = mercadoPagoService.processMerchantOrder("merchant_order", "/v1/merchant_orders/123", 1L);

        assertTrue(result);
    }

    @Test
    @DisplayName("MP-003/Should process merchant order as unpaid and refund")
    void testProcessMerchantOrderUnpaidWithRefund() throws MPException, MPApiException {
        MerchantOrderPayment payment1 = mock(MerchantOrderPayment.class);
        given(payment1.getId()).willReturn(1L);

        MerchantOrder mockOrder = mock(MerchantOrder.class);
        given(mockOrder.getOrderStatus()).willReturn("pending");
        given(mockOrder.getPayments()).willReturn(List.of(payment1));

        given(merchantOrderClient.get(123L)).willReturn(mockOrder);

        boolean result = mercadoPagoService.processMerchantOrder("merchant_order", "/v1/merchant_orders/123", 1L);

        verify(paymentRefundClient).refund(1L);
        assertFalse(result);
    }

    @Test
    @DisplayName("MP-004/Should not process if topic is not merchant_order")
    void testProcessInvalidTopic() throws MPException, MPApiException {
        boolean result = mercadoPagoService.processMerchantOrder("payment", "/v1/merchant_orders/123", 1L);
        assertFalse(result);
    }
}

