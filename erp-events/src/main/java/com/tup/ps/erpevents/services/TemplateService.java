package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.notification.template.TemplateDTO;
import com.tup.ps.erpevents.dtos.notification.template.TemplatePostDTO;
import com.tup.ps.erpevents.dtos.notification.template.TemplatePutDTO;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface TemplateService {

    TemplateDTO createEmailTemplate(TemplatePostDTO emailTemplateDTO) throws MessagingException;


    TemplateDTO getEmailTemplateById(Long id);


    List<TemplateDTO> getAllEmailTemplate(Boolean hasPlaceholders, String name);


    Page<TemplateDTO> getAllEmailTemplate(Pageable pageable, Boolean active, Boolean hasPlaceholders, String name);


    TemplateDTO updateEmailTemplate(TemplatePutDTO updateDto) throws MessagingException;


    Boolean deleteEmailTemplate(Long id);
}
