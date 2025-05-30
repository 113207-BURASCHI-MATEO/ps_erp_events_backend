package com.tup.ps.erpevents.services;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tup.ps.erpevents.dtos.payment.PaymentDTO;
import com.tup.ps.erpevents.dtos.payment.PaymentPostDTO;
import com.tup.ps.erpevents.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface PaymentService {

    Page<PaymentDTO> findAll(Pageable pageable, Boolean isActive);

    Optional<PaymentDTO> findById(Long id);

    PaymentDTO save(PaymentPostDTO dto) throws MPException, MPApiException;

    PaymentDTO updateStatus(Long id, PaymentStatus status);

    Page<PaymentDTO> findByFilters(Pageable pageable,
                                   PaymentStatus status,
                                   String searchValue,
                                   LocalDate paymentDateStart,
                                   LocalDate paymentDateEnd);

    void receiveNotification(String topic, String resource, Long clientId) throws MPException, MPApiException;
}
