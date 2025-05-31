package com.tup.ps.erpevents.services;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.dtos.payment.PaymentDTO;
import com.tup.ps.erpevents.dtos.payment.PaymentPostDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.enums.PaymentStatus;
import com.tup.ps.erpevents.repositories.ClientRepository;
import com.tup.ps.erpevents.repositories.PaymentRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.PaymentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private MercadoPagoService mercadoPagoService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private GenericSpecification<PaymentEntity> specification;
    @Mock
    private NotificationService notificationService;

    @Mock
    private PaymentEntity paymentEntity;
    @Mock
    private ClientEntity clientEntity;
    @Mock
    private PaymentPostDTO paymentPostDTO;
    @Mock
    private PaymentDTO paymentDTO;
    @Mock
    private Preference preference;

    @Test
    @DisplayName("PAY-001/Should save a payment and send notification")
    void testSavePayment() throws MPException, MPApiException {
        given(paymentPostDTO.getIdClient()).willReturn(1L);
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        given(modelMapper.map(paymentPostDTO, PaymentEntity.class)).willReturn(paymentEntity);
        given(paymentRepository.save(paymentEntity)).willReturn(paymentEntity);
        given(mercadoPagoService.createPreference(paymentEntity)).willReturn(preference);
        given(preference.getSandboxInitPoint()).willReturn("http://sandbox-url.com");
        given(modelMapper.map(paymentEntity, PaymentDTO.class)).willReturn(paymentDTO);
        given(paymentEntity.getAmount()).willReturn(1500.0);
        given(clientEntity.getFirstName()).willReturn("John");
        given(clientEntity.getLastName()).willReturn("Doe");
        given(clientEntity.getIdClient()).willReturn(1L);

        PaymentDTO result = paymentService.save(paymentPostDTO);

        assertNotNull(result);
        verify(notificationService).sendEmailToClient(any(NotificationPostDTO.class), eq(clientEntity));
    }

    @Test
    @DisplayName("PAY-002/Should update payment status")
    void testUpdatePaymentStatus() {
        given(paymentRepository.findById(1L)).willReturn(Optional.of(paymentEntity));
        given(paymentRepository.save(paymentEntity)).willReturn(paymentEntity);
        given(modelMapper.map(paymentEntity, PaymentDTO.class)).willReturn(paymentDTO);
        given(paymentEntity.getStatus()).willReturn(PaymentStatus.PENDING_PAYMENT);

        PaymentDTO result = paymentService.updateStatus(1L, PaymentStatus.PAID);

        assertNotNull(result);
        verify(paymentRepository).save(paymentEntity);
    }

    @Test
    @DisplayName("PAY-003/Should receive notification and update latest payment")
    void testReceiveNotificationAndMarkAsPaid() throws MPException, MPApiException {
        given(mercadoPagoService.processMerchantOrder("merchant_order", "/v1/merchant_orders/123", 1L))
                .willReturn(true);
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        given(paymentRepository.findByClientOrderByCreationDateDesc(clientEntity))
                .willReturn(List.of(paymentEntity));

        paymentService.receiveNotification("merchant_order", "/v1/merchant_orders/123", 1L);

        verify(paymentEntity).setStatus(PaymentStatus.PAID);
        verify(paymentRepository).save(paymentEntity);
    }

    @Test
    @DisplayName("PAY-004/Should throw exception when trying to revert paid payment")
    void testUpdatePaymentStatusIllegalChange() {
        given(paymentRepository.findById(1L)).willReturn(Optional.of(paymentEntity));
        given(paymentEntity.getStatus()).willReturn(PaymentStatus.PAID);

        assertThrows(IllegalStateException.class, () -> {
            paymentService.updateStatus(1L, PaymentStatus.PENDING_PAYMENT);
        });
    }
}

