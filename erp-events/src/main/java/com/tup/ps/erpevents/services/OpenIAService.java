package com.tup.ps.erpevents.services;

import org.springframework.stereotype.Service;

@Service
public interface OpenIAService {

    String getChatGPTResponse(String request);
}
