package com.tup.ps.erpevents.services.impl;


import com.tup.ps.erpevents.dtos.notification.template.TemplateDTO;
import com.tup.ps.erpevents.dtos.notification.template.TemplatePostDTO;
import com.tup.ps.erpevents.dtos.notification.template.TemplatePutDTO;
import com.tup.ps.erpevents.entities.TemplateEntity;
import com.tup.ps.erpevents.repositories.TemplateRepository;
import com.tup.ps.erpevents.services.TemplateService;
import com.tup.ps.erpevents.services.utils.VariableUtils;
import jakarta.mail.MessagingException;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class TemplateServiceImpl implements TemplateService {


    @Autowired
    private TemplateRepository templateRepository;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public TemplateDTO createEmailTemplate(TemplatePostDTO emailTemplateDTO) throws MessagingException {
        byte[] decodedBytes = Base64.getDecoder().decode(emailTemplateDTO.getBase64body());
        String htmlContent = new String(decodedBytes);

        if (validateHTML(htmlContent)) {
            TemplateEntity entity = new TemplateEntity();
            entity.setBody(htmlContent);
            entity.setName(emailTemplateDTO.getName());
            entity.setActive(true);
            TemplateEntity saved =
                    templateRepository.save(entity);

            return modelMapper.map(saved, TemplateDTO.class);
        } else {
            throw new MessagingException("Error creating template, the HTML body is INVALID");
        }
    }

    /**
     * Gets a template with the associated identification.
     * @param id which is the template ID
     * @return the associated template or else a null
     */
    @Override
    public TemplateDTO getEmailTemplateById(Long id) {
        Optional<TemplateEntity> optTemplate =
                templateRepository.findById(id);

        if (optTemplate.isEmpty()) {
            //throw new NotFoundException("Template not found");
            throw new RuntimeException("Template not found");
        }

        return modelMapper.map(optTemplate.get(), TemplateDTO.class);
    }


    /**
     Gets all email templates available in the Database.
     * @return a list of all Email Template in the Database
     */
    @Override
    public List<TemplateDTO> getAllEmailTemplate(Boolean hasPlaceholders, String name) {
        List<TemplateEntity> entities = templateRepository.findAll();

        List<TemplateEntity> foundTemplateEntities = Boolean.TRUE.equals(hasPlaceholders)
                ? checkTemplatesForVariables(entities)
                : entities;

        if (name != null) {
            foundTemplateEntities = foundTemplateEntities.stream()
                    .filter(x -> x.getName().toLowerCase(Locale.ENGLISH).contains(name.toLowerCase(Locale.ENGLISH)))
                    .toList();
        }

        List<TemplateDTO> emailTemplateList = new ArrayList<>();

        for (TemplateEntity entity : foundTemplateEntities) {
            emailTemplateList.add(modelMapper.map(entity, TemplateDTO.class));
        }

        return emailTemplateList;
    }


    @Override
    public Page<TemplateDTO> getAllEmailTemplate(Pageable pageable, Boolean active, Boolean hasPlaceholders, String name) {
        Specification<TemplateEntity> spec = com.tup.ps.erpevents.specifications.TemplateSpecifications.createSpecification(active, hasPlaceholders, name);

        Page<TemplateEntity> emailTemplateEntityPage = templateRepository.findAll(spec, pageable);

        List<TemplateDTO> emailTemplates = emailTemplateEntityPage.getContent().stream()
                .map(entity -> modelMapper.map(entity, TemplateDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(emailTemplates, pageable, emailTemplateEntityPage.getTotalElements());
    }


    @Override
    public TemplateDTO updateEmailTemplate(TemplatePutDTO updateDto) throws MessagingException {
        String htmlContent = new String(Base64.getDecoder().decode(updateDto.getBase64body()));
        if (validateHTML(htmlContent)) {
            TemplateDTO emailTemplate = this.getEmailTemplateById(updateDto.getId());
            emailTemplate.setName(updateDto.getName());
            emailTemplate.setBody(updateDto.getBase64body());

            TemplateEntity savedTemplate = templateRepository.save(modelMapper.map(emailTemplate, TemplateEntity.class));
            return modelMapper.map(savedTemplate, TemplateDTO.class);
        } else {
            throw new MessagingException("Error updating template, the HTML body is INVALID");
        }

    }


    @Override
    public Boolean deleteEmailTemplate(Long id) {
        Optional<TemplateEntity> templateEntity
                = templateRepository.findById(id);

        if (templateEntity.isEmpty()) {
            //throw new NotFoundException("Template not found");
            throw new RuntimeException("Template not found");
        }

        templateEntity.get().setActive(false);
        templateRepository.save(templateEntity.get());
        return true;
    }


    private Boolean validateHTML(String html) {
        Tidy tidy = new Tidy();

        try (StringReader input = new StringReader(html);
             StringWriter output = new StringWriter()) {

            tidy.parse(input, output);

            return tidy.getParseErrors() <= 0;
        } catch (Exception e) {
            return false;
        }
    }

    private List<TemplateEntity> checkTemplatesForVariables(List<TemplateEntity> allTemplates) {
        return allTemplates.stream()
                .filter(templateEntity -> !VariableUtils.extractPlaceholders(templateEntity.getBody()).isEmpty())
                .collect(Collectors.toList());
    }
}
