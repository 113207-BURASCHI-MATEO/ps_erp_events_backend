package com.tup.ps.erpevents.services.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentRefundClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.merchantorder.MerchantOrderPayment;
import com.mercadopago.resources.preference.Preference;
import com.tup.ps.erpevents.dtos.payment.PaymentPostDTO;
import com.tup.ps.erpevents.entities.PaymentEntity;
import com.tup.ps.erpevents.services.MercadoPagoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Autowired
    private PreferenceClient preferenceClient;
    @Autowired
    private MerchantOrderClient merchantOrderClient;
    @Autowired
    private PaymentRefundClient paymentRefundClient;

    @Value("${front.url}")
    private String WEB_URL;

    @Value("${back.url}")
    private String BACK_URL;

    @Value("${mercado.pago.token}")
    private String MP_TOKEN;

    private static final String CURRENCY = "ARS";

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(MP_TOKEN);
    }

    /*public Preference createPreference(PaymentEntity payment) throws MPException, MPApiException {
        PreferenceItemRequest preferenceItemRequest = PreferenceItemRequest.builder()
                .id(String.valueOf(payment.getIdPayment()))
                .title("ERP-Eventos - Pago de Cliente " + payment.getClient().getIdClient())
                .description(payment.getDetail() != null ? payment.getDetail() : "Pago de servicios")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(payment.getAmount()))
                .currencyId(CURRENCY)
                .build();

        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success(WEB_URL + "/payments/success")
                .failure(WEB_URL + "/payments/failure")
                .pending(WEB_URL + "/payments/pending")
                .build();

        String notificationUrl = BACK_URL + "/payments/notification/" + payment.getClient().getIdClient();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(preferenceItemRequest))
                .backUrls(backUrlsRequest)
                .notificationUrl(notificationUrl)
                .autoReturn("approved")
                .expires(true)
                .expirationDateTo(OffsetDateTime.now().plusMinutes(10L))
                .build();

        return preferenceClient.create(preferenceRequest);
    }*/

    @Override
    public Preference createPreference(PaymentEntity payment) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(MP_TOKEN);

        String notificationUrl = BACK_URL + "/payments/notification/" + payment.getClient().getIdClient();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(WEB_URL)
                .pending(WEB_URL)
                .failure(WEB_URL)
                .build();

        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id(String.valueOf(payment.getIdPayment()))
                .title("ERP-Eventos - Pago de Cliente " + payment.getClient().getIdClient())
                .description(payment.getDetail() != null ? payment.getDetail() : "Pago de servicios")
                .pictureUrl("https://cdn.pixabay.com/photo/2022/06/11/13/47/planner-7256390_1280.jpg")
                .categoryId("services")
                .quantity(1)
                .currencyId(CURRENCY)
                .unitPrice(BigDecimal.valueOf(payment.getAmount()))
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .notificationUrl(notificationUrl)
                //.autoReturn("approved")
                .expires(true)
                .expirationDateTo(OffsetDateTime.now().plusMinutes(10L))
                .build();

        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }

    @Override
    public boolean processMerchantOrder(String topic, String resource, Long clientId) throws MPException, MPApiException {
        if ("merchant_order".equalsIgnoreCase(topic) && resource != null) {
            Long merchantOrderId = Long.parseLong(resource.substring(resource.lastIndexOf('/') + 1));
            MerchantOrder merchantOrder = merchantOrderClient.get(merchantOrderId);

            if ("paid".equalsIgnoreCase(merchantOrder.getOrderStatus())) {
                return true;
            } else {
                for (MerchantOrderPayment payment : merchantOrder.getPayments()) {
                    paymentRefundClient.refund(payment.getId());
                }
            }
        }
        return false;
    }


}
