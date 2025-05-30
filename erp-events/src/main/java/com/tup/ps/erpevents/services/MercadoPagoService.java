package com.tup.ps.erpevents.services;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tup.ps.erpevents.dtos.payment.PaymentPostDTO;
import com.tup.ps.erpevents.entities.PaymentEntity;
import org.springframework.stereotype.Service;

@Service
public interface MercadoPagoService {

    Preference createPreference(PaymentEntity paymentEntity) throws MPException, MPApiException;

    boolean processMerchantOrder(String topic, String resource, Long clientId) throws MPException, MPApiException;

}
