package com.tup.ps.erpevents.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tup.ps.erpevents.dtos.payment.PaymentDTO;
import com.tup.ps.erpevents.dtos.payment.PaymentPostDTO;
import com.tup.ps.erpevents.enums.PaymentStatus;
import com.tup.ps.erpevents.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@Validated
@RequiredArgsConstructor
@Tag(name = "Pagos", description = "Gestión de pagos del sistema")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Obtener todos los pagos")
    @GetMapping("")
    public ResponseEntity<Page<PaymentDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(value = "isActive", required = false, defaultValue = "true") Boolean isActive,
                                                   @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
                                                   @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(paymentService.findAll(pageable, isActive));
    }

    @Operation(summary = "Obtener pago por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getById(@PathVariable Long id) {
        return paymentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
    }

    @Operation(summary = "Crear nuevo pago")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PaymentDTO> create(@Valid @RequestBody PaymentPostDTO dto) throws MPException, MPApiException {
        PaymentDTO created = paymentService.save(dto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("MercadoPago-InitPoint", created.getReviewNote());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(created);
    }

    @PostMapping("/notification/{clientId}")
    public ResponseEntity<Void> receiveNotification(@RequestBody Map<String, Object> body,
                                                 @PathVariable Long clientId)
            throws MPException, MPApiException {

        String topic = (String) body.get("topic");
        String resource = (String) body.get("resource");
        paymentService.receiveNotification(topic, resource, clientId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar estado del pago")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updateStatus(@PathVariable Long id, @RequestBody PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updateStatus(id, status));
    }


    @Operation(summary = "Filtrar pagos")
    @GetMapping("/filter")
    public ResponseEntity<Page<PaymentDTO>> filterPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) PaymentStatus status,
            @RequestParam(required = false) String searchValue,
            @RequestParam(required = false) LocalDate paymentDateStart,
            @RequestParam(required = false) LocalDate paymentDateEnd,
            @RequestParam(name = "sort", defaultValue = "creationDate") String sortProperty,
            @RequestParam(name = "sort_direction", defaultValue = "DESC") Sort.Direction sortDirection) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty.split(",")));
        return ResponseEntity.ok(paymentService.findByFilters(pageable, status, searchValue, paymentDateStart, paymentDateEnd));
    }

}
