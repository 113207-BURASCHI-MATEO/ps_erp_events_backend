package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.notification.KeyValueCustomPair;
import com.tup.ps.erpevents.dtos.notification.NotificationDTO;
import com.tup.ps.erpevents.dtos.notification.NotificationPostDTO;
import com.tup.ps.erpevents.dtos.notification.template.TemplateDTO;
import com.tup.ps.erpevents.dtos.user.UserDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.enums.StatusSend;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.services.impl.NotificationServiceImpl;
import com.tup.ps.erpevents.services.impl.UserServiceImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import org.springframework.mail.javamail.JavaMailSender;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private TemplateService templateService;
    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private UserDTO user;
    private ClientEntity client;
    private TemplateDTO template;
    private NotificationPostDTO notificationPostDTO;

    @BeforeEach
    void setUp() {
        user = new UserDTO();
        user.setIdUser(1L);
        user.setEmail("test@example.com");

        client = new ClientEntity();
        client.setIdClient(1L);
        client.setEmail("client@example.com");
        client.setFirstName("Juan");
        client.setLastName("Perez");

        template = new TemplateDTO();
        template.setName("TestTemplate");
        template.setBody("<html><body>Hello {{FIRST_NAME}} {{LAST_NAME}}</body></html>");

        notificationPostDTO = new NotificationPostDTO();
        notificationPostDTO.setSubject("Test Subject");
        notificationPostDTO.setIdTemplate(1L);
        notificationPostDTO.setContactIds(List.of(1L));
        notificationPostDTO.setVariables(List.of(
                new KeyValueCustomPair("FIRST_NAME", "Juan"),
                new KeyValueCustomPair("LAST_NAME", "Perez")
        ));
    }

    @Test
    @DisplayName("NT-001/Should send email to contacts and save notifications")
    void testSendEmailToClient() throws Exception {
        given(templateService.getEmailTemplateById(1L)).willReturn(template);

        MimeMessage message = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(message);

        notificationService.sendEmailToClient(notificationPostDTO, client);

        verify(mailSender).send(any(MimeMessage.class));
        verify(notificationRepository).save(any(NotificationEntity.class));
    }

    @Test
    @DisplayName("NT-002/Should send email to client and save notification")
    void testSendEmailToContacts() throws Exception {
        given(userService.findUsersByIds(List.of(1L))).willReturn(List.of(user));
        given(templateService.getEmailTemplateById(1L)).willReturn(template);

        MimeMessage message = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(message);

        notificationService.sendEmailToContacts(notificationPostDTO);

        verify(mailSender).send(any(MimeMessage.class));
        verify(notificationRepository).save(any(NotificationEntity.class));
    }

    @Test
    @DisplayName("NT-003/Should get notifications for a user")
    void testGetNotifications() {
        NotificationEntity entity = new NotificationEntity();
        entity.setRecipient("user@example.com");
        entity.setIdContact(1L);
        entity.setStatusSend(StatusSend.SENT);

        NotificationDTO dto = new NotificationDTO();

        given(userService.getUserById(1L)).willReturn(user);
        given(notificationRepository.findByIdContactAndStatusSend(1L, StatusSend.SENT))
                .willReturn(List.of(entity));
        given(modelMapper.map(any(), eq(NotificationDTO.class))).willReturn(dto);

        List<NotificationDTO> result = notificationService.getNotifications(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("NT-004/Should visualize a notification")
    void testVisualizeNotification() {
        NotificationEntity entity = new NotificationEntity();
        entity.setStatusSend(StatusSend.SENT);

        given(notificationRepository.findById(1L)).willReturn(Optional.of(entity));

        notificationService.visualizeNotification(1L);

        verify(notificationRepository).save(entity);
        assertEquals(StatusSend.VISUALIZED, entity.getStatusSend());
    }
}

