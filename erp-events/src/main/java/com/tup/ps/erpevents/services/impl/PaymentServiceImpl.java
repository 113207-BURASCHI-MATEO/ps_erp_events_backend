package com.tup.ps.erpevents.services.impl;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.notification.KeyValueCustomPair;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.dtos.payment.PaymentDTO;
import com.tup.ps.erpevents.dtos.payment.PaymentPostDTO;
import com.tup.ps.erpevents.entities.ClientEntity;
import com.tup.ps.erpevents.entities.EventEntity;
import com.tup.ps.erpevents.entities.PaymentEntity;
import com.tup.ps.erpevents.enums.PaymentStatus;
import com.tup.ps.erpevents.repositories.ClientRepository;
import com.tup.ps.erpevents.repositories.PaymentRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.MercadoPagoService;
import com.tup.ps.erpevents.services.NotificationService;
import com.tup.ps.erpevents.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final String[] PAYMENT_FIELDS = {
            "detail"
    };

    @Value("${mercado.pago.token}")
    private String MP_TOKEN;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MercadoPagoService mercadoPagoService;
    @Autowired
    private ClientRepository clientRepository;
    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenericSpecification<PaymentEntity> specification;
    @Autowired
    private NotificationService notificationService;


    @Override
    @Transactional
    public Page<PaymentDTO> findAll(Pageable pageable, Boolean isActive) {
        return paymentRepository.findAll(pageable)
                .map(payment -> {
                    PaymentDTO dto = modelMapper.map(payment, PaymentDTO.class);
                    dto.setIdClient(payment.getClient().getIdClient());
                    return dto;
                });
    }

    @Override
    @Transactional
    public Optional<PaymentDTO> findById(Long id) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    PaymentDTO dto = modelMapper.map(payment, PaymentDTO.class);
                    dto.setIdClient(payment.getClient().getIdClient());
                    return dto;
                });
    }

    @Override
    @Transactional
    public PaymentDTO save(PaymentPostDTO dto) throws MPException, MPApiException {
        PaymentEntity payment = modelMapper.map(dto, PaymentEntity.class);
        ClientEntity client = clientRepository.findById(dto.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        payment.setClient(client);
        payment.setStatus(PaymentStatus.PENDING_PAYMENT);

        PaymentEntity savedPayment = paymentRepository.save(payment);

        Preference preference = mercadoPagoService.createPreference(savedPayment);

        PaymentDTO paymentDTO = modelMapper.map(savedPayment, PaymentDTO.class);
        paymentDTO.setReviewNote(preference.getSandboxInitPoint());

        // Enviar notificación
        NotificationPostDTO notification = new NotificationPostDTO();
        notification.setContactIds(List.of(client.getIdClient()));
        notification.setSubject("ERP Eventos - Nuevo Pago Pendiente");
        notification.setIdTemplate(5L);

        List<KeyValueCustomPair> variables = new ArrayList<>();
        variables.add(new KeyValueCustomPair("FIRST_NAME", client.getFirstName()));
        variables.add(new KeyValueCustomPair("LAST_NAME", client.getLastName()));
        variables.add(new KeyValueCustomPair("AMOUNT", String.valueOf(payment.getAmount())));
        variables.add(new KeyValueCustomPair("PAYMENT_URL", preference.getSandboxInitPoint()));
        notification.setVariables(variables);

        notificationService.sendEmailToClient(notification, client);

        return paymentDTO;
    }


    @Override
    public PaymentDTO updateStatus(Long id, PaymentStatus status) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));
        if (payment.getStatus() == PaymentStatus.PAID && status == PaymentStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("No se puede cambiar el estado de PAID a PENDING_PAYMENT");
        }
        payment.setStatus(status);
        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }


    @Override
    public Page<PaymentDTO> findByFilters(Pageable pageable,
                                          PaymentStatus status,
                                          String searchValue,
                                          LocalDate paymentDateStart,
                                          LocalDate paymentDateEnd) {
        Map<String, Object> filters = new HashMap<>();

        if (status != null) {
            filters.put("status", PaymentStatus.valueOf(String.valueOf(status)));
        }

        Specification<PaymentEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<PaymentEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, PAYMENT_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((paymentDateStart == null) ^ (paymentDateEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (paymentDateStart != null && paymentDateEnd != null) {
            if (paymentDateStart.isAfter(paymentDateEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "paymentDateStart no puede ser mayor a paymentDateEnd");
            }
            Specification<PaymentEntity> dateSpec = specification.filterBetween(
                    paymentDateStart, paymentDateEnd, "paymentDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return paymentRepository.findAll(spec, pageable)
                .map(payment -> modelMapper.map(payment, PaymentDTO.class));
    }

    @Override
    public void receiveNotification(String topic, String resource, Long clientId) throws MPException, MPApiException {
        boolean isPaid = mercadoPagoService.processMerchantOrder(topic, resource, clientId);
        if (isPaid) {
            ClientEntity clientEntity = clientRepository.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
            List<PaymentEntity> payments = paymentRepository.findByClientOrderByCreationDateDesc(clientEntity);
            if (!payments.isEmpty()) {
                PaymentEntity latestPayment = payments.get(0);
                latestPayment.setStatus(PaymentStatus.PAID);
                paymentRepository.save(latestPayment);
            }
        }
    }
}
