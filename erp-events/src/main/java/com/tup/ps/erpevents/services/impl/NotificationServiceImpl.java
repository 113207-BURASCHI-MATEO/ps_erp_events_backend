package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.notification.KeyValueCustomPair;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.dtos.notification.NotificationDTO;
import com.tup.ps.erpevents.dtos.notification.template.TemplateDTO;
import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.entities.ClientEntity;
import com.tup.ps.erpevents.entities.GuestEntity;
import com.tup.ps.erpevents.entities.NotificationEntity;
import com.tup.ps.erpevents.enums.StatusSend;
import com.tup.ps.erpevents.repositories.NotificationRepository;
import com.tup.ps.erpevents.services.NotificationService;
import com.tup.ps.erpevents.services.TemplateService;
import com.tup.ps.erpevents.services.utils.VariableUtils;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;
import jakarta.mail.internet.MimeMessage;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JavaMailSender mailSender;

    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private NotificationRepository notificationRepository;

    private static final String EMAIL_FROM =
            "noreplyerpeventsnotifications@gmail.com";



    @Override
    public void sendEmailToContacts(NotificationPostDTO notificationPostDTO) {

        List<UserDTO> users = userService.findUsersByIds(notificationPostDTO.getContactIds());

        TemplateDTO emailTemplate = templateService.getEmailTemplateById(notificationPostDTO.getIdTemplate());

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTemplateName(emailTemplate.getName());
        notificationDTO.setIdTemplate(notificationPostDTO.getIdTemplate());
        notificationDTO.setSubject(notificationPostDTO.getSubject());
        notificationDTO.setVariables(notificationPostDTO.getVariables());


        String processedBody = processTemplate(emailTemplate.getBody(), notificationDTO.getVariables());
        notificationDTO.setBody(processedBody);

        for (UserDTO user : users) {
            notificationDTO.setRecipient(user.getEmail());
            notificationDTO.setIdContact(user.getIdUser());

            sendEmail(notificationDTO.getRecipient(), notificationDTO.getSubject(), processedBody);
            saveNotification(notificationDTO);
        }

    }

    @Override
    public void sendEmailToClient(NotificationPostDTO notificationPostDTO, ClientEntity clientEntiy) {

        TemplateDTO emailTemplate = templateService.getEmailTemplateById(notificationPostDTO.getIdTemplate());

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTemplateName(emailTemplate.getName());
        notificationDTO.setIdTemplate(notificationPostDTO.getIdTemplate());
        notificationDTO.setSubject(notificationPostDTO.getSubject());
        notificationDTO.setVariables(notificationPostDTO.getVariables());


        String processedBody = processTemplate(emailTemplate.getBody(), notificationDTO.getVariables());
        notificationDTO.setBody(processedBody);

        notificationDTO.setRecipient(clientEntiy.getEmail());
        notificationDTO.setIdContact(clientEntiy.getIdClient());

        sendEmail(notificationDTO.getRecipient(), notificationDTO.getSubject(), processedBody);
        saveNotification(notificationDTO);

    }

    @Override
    public void sendEmailToGuest(NotificationPostDTO notificationPostDTO, GuestEntity guestEntity) {

        TemplateDTO emailTemplate = templateService.getEmailTemplateById(notificationPostDTO.getIdTemplate());

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTemplateName(emailTemplate.getName());
        notificationDTO.setIdTemplate(notificationPostDTO.getIdTemplate());
        notificationDTO.setSubject(notificationPostDTO.getSubject());
        notificationDTO.setVariables(notificationPostDTO.getVariables());


        String processedBody = processTemplate(emailTemplate.getBody(), notificationDTO.getVariables());
        notificationDTO.setBody(processedBody);

        notificationDTO.setRecipient(guestEntity.getEmail());
        notificationDTO.setIdContact(guestEntity.getIdGuest());

        sendEmail(notificationDTO.getRecipient(), notificationDTO.getSubject(), processedBody);
        saveNotification(notificationDTO);
    }

    @Override
    public List<NotificationDTO> getNotifications(Long idUser) {
        userService.getUserById(idUser);
        List<NotificationEntity> notificationEntities = notificationRepository
                .findByIdContactAndStatusSend(idUser, StatusSend.SENT);
        return notificationEntities.stream()
                .map(notificationEntity ->
                        modelMapper.map(notificationEntity, NotificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void visualizeNotification(Long idNotification) {
        NotificationEntity entity = notificationRepository.findById(idNotification)
                .orElseThrow(() -> new EntityNotFoundException("Notificacion no encontrado"));
        entity.setStatusSend(StatusSend.VISUALIZED);
        notificationRepository.save(entity);
    }

    @SneakyThrows
    private void sendEmail(String to, String subject, String body) {
        if (validateHTML(body)) {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setText(body, true);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setFrom(EMAIL_FROM);

                mailSender.send(mimeMessage);

            } catch (MessagingException e) {
                throw new MessagingException("Error sending email", e);
            }
        } else {
            throw new MessagingException("Error sending email, the HTML body is INVALID");
        }

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

    private String processTemplate(String template, List<KeyValueCustomPair> variables) {
        Set<String> requiredKeys = VariableUtils.extractPlaceholders(template);

        // Extract keys from the provided variables
        Set<String> providedKeys = variables.stream()
                .map(KeyValueCustomPair::getKey)
                .collect(Collectors.toSet());

        // If keys do not match, throw a BadRequestException
        if (!requiredKeys.equals(providedKeys)) {
            throw new IllegalArgumentException("Required keys not match");
        }

        String processedTemplate = template;

        // Replace placeholders with values
        for (KeyValueCustomPair entry : variables) {
            processedTemplate = processedTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return processedTemplate;
    }

   private void saveNotification(NotificationDTO notificationDTO) {
        NotificationEntity entity = new NotificationEntity();
        entity.setRecipient(notificationDTO.getRecipient());
        if (notificationDTO.getIdContact() != null) {
            entity.setIdContact(notificationDTO.getIdContact());
        }
        entity.setSubject(notificationDTO.getSubject());
        entity.setIdTemplate(notificationDTO.getIdTemplate());
        entity.setTemplateName(notificationDTO.getTemplateName());
        entity.setBody(notificationDTO.getBody());
        entity.setDateSend(LocalDateTime.now());
        entity.setStatusSend(StatusSend.SENT);

        notificationRepository.save(entity);

    }
}
