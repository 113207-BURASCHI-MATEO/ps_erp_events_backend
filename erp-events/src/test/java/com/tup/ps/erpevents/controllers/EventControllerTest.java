package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.EventPostDTO;
import com.tup.ps.erpevents.dtos.event.EventPutDTO;
import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.location.LocationPostDTO;
import com.tup.ps.erpevents.enums.EventStatus;
import com.tup.ps.erpevents.enums.EventType;
import com.tup.ps.erpevents.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventService eventService;

    private EventDTO event;
    private EventPostDTO eventPost;
    private EventPutDTO eventPut;

    @BeforeEach
    void setUp() {
        event = new EventDTO();
        event.setIdEvent(1L);
        event.setTitle("Fiesta de Fin de Año");
        event.setDescription("Evento anual de la empresa");
        event.setEventType(EventType.CORPORATE);
        event.setStartDate(LocalDateTime.now());
        event.setEndDate(LocalDateTime.now().plusHours(4));
        event.setStatus(EventStatus.CONFIRMED);
        event.setSoftDelete(false);
        event.setClient(new ClientDTO());
        event.setLocation(new LocationDTO());
        event.setEmployeesIds(List.of(1L, 2L));
        event.setSuppliersIds(List.of(1L, 3L));
        event.setEmployees(List.of());
        event.setSuppliers(List.of());
        event.setGuests(List.of(1L, 2L));
        event.setTasks(List.of());

        eventPost = new EventPostDTO();
        eventPost.setTitle("Fiesta de Fin de Año");
        eventPost.setDescription("Evento anual de la empresa");
        eventPost.setEventType(EventType.CULTURAL);
        eventPost.setStartDate(LocalDateTime.now());
        eventPost.setEndDate(LocalDateTime.now().plusHours(4));
        eventPost.setStatus(EventStatus.CONFIRMED);
        eventPost.setClientId(1L);
        eventPost.setClient(new ClientPostDTO());
        eventPost.setLocationId(2L);
        eventPost.setLocation(new LocationPostDTO());
        eventPost.setEmployeeIds(List.of(1L, 2L));
        eventPost.setSupplierIds(List.of(1L, 3L));
        eventPost.setTasks(List.of());

        eventPut = new EventPutDTO();
        eventPut.setIdEvent(1L);
        eventPut.setTitle("Fiesta actualizada");
        eventPut.setDescription("Descripción actualizada");
        eventPut.setEventType(EventType.ENTERTAINMENT);
        eventPut.setStartDate(LocalDateTime.now());
        eventPut.setEndDate(LocalDateTime.now().plusHours(4));
        eventPut.setStatus(EventStatus.IN_PROGRESS);
        eventPut.setLocationId(2L);
        eventPut.setEmployeeIds(List.of(1L, 2L));
        eventPut.setSupplierIds(List.of(1L, 3L));
    }

    @Test
    @DisplayName("EC-001/Should return all events successfully")
    void testGetAllEventsSuccess() throws Exception {
        Page<EventDTO> page = new PageImpl<>(List.of(event));
        given(eventService.findAll(any())).willReturn(page);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Fiesta de Fin de Año"));
    }

    @Test
    @DisplayName("EC-002/Should return event by ID successfully")
    void testGetEventByIdSuccess() throws Exception {
        given(eventService.findById(1L)).willReturn(Optional.of(event));

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Fiesta de Fin de Año"));
    }

    @Test
    @DisplayName("EC-003/Should return 404 when event is not found")
    void testGetEventByIdNotFound() throws Exception {
        given(eventService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Evento no encontrado"));
    }

    @Test
    @DisplayName("EC-004/Should create event successfully")
    void testCreateEventSuccess() throws Exception {
        given(eventService.save(any(EventPostDTO.class))).willReturn(event);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Fiesta de Fin de Año"));
    }

    @Test
    @DisplayName("EC-005/Should update event successfully")
    void testUpdateEventSuccess() throws Exception {
        given(eventService.update(eq(1L), any(EventPutDTO.class))).willReturn(event);

        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Fiesta de Fin de Año"));
    }

    @Test
    @DisplayName("EC-006/Should delete event successfully")
    void testDeleteEventSuccess() throws Exception {
        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("EC-007/Should update event status successfully")
    void testUpdateEventStatusSuccess() throws Exception {
        given(eventService.eventStatus(eq(1L), any(EventStatus.class))).willReturn(event);

        mockMvc.perform(put("/events/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(EventStatus.CANCELLED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    @DisplayName("EC-008/Should return events by filters successfully")
    void testFilterEventsSuccess() throws Exception {
        Page<EventDTO> page = new PageImpl<>(List.of(event));
        given(eventService.findByFilters(any(), any(), any(), any(), any(), any(), any())).willReturn(page);

        mockMvc.perform(get("/events/filter")
                        .param("eventType", "CORPORATE")
                        .param("eventStatus", "CONFIRMED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Fiesta de Fin de Año"));
    }
}

