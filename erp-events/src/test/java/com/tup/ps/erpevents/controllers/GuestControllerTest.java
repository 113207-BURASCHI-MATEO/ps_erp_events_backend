package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.guest.GuestAccessDTO;
import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPutDTO;
import com.tup.ps.erpevents.enums.AccessType;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.enums.GuestType;
import com.tup.ps.erpevents.services.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GuestService guestService;

    private GuestDTO guest;
    private GuestPostDTO guestPost;
    private GuestPutDTO guestPut;
    private GuestAccessDTO guestAccess;

    @BeforeEach
    void setUp() {
        guest = new GuestDTO();
        guest.setIdGuest(1L);
        guest.setFirstName("Carla");
        guest.setLastName("Lopez");
        guest.setEmail("carla.lopez@example.com");
        guest.setPhoneNumber("3512345678");
        guest.setDocumentNumber("12345678");
        guest.setDocumentType(DocumentType.DNI);
        guest.setSoftDelete(false);

        guestPost = new GuestPostDTO();
        guestPost.setFirstName("Carla");
        guestPost.setLastName("Lopez");
        guestPost.setEmail("carla.lopez@example.com");
        guestPost.setPhoneNumber("3512345678");
        guestPost.setDocumentNumber("12345678");
        guestPost.setDocumentType(DocumentType.DNI);

        guestPost.setIdEvent(100L);
        guestPost.setBirthDate(LocalDate.of(1990, 1, 1));
        guestPost.setType(GuestType.GENERAL);

        guestPost.setNote("Invitada por RRPP");
        guestPost.setSector("A");
        guestPost.setRowTable("1");
        guestPost.setSeat(3);
        guestPost.setFoodRestriction(false);
        guestPost.setFoodDescription(null);

        guestPut = new GuestPutDTO();
        guestPut.setIdGuest(1L);
        guestPut.setFirstName("Carla");
        guestPut.setLastName("Lopez");
        guestPut.setEmail("carla.lopez@example.com");
        guestPut.setPhoneNumber("3512345678");
        guestPut.setDocumentNumber("12345678");
        guestPut.setDocumentType(DocumentType.DNI);

        guestPut.setIdEvent(100L);
        guestPut.setBirthDate(LocalDate.of(1990, 1, 1));
        guestPut.setType(GuestType.GENERAL);

        guestPut.setSector("A1");
        guestPut.setRowTable("1");
        guestPut.setSeat(3);
        guestPut.setNote("Sin acompañante");
        guestPut.setFoodRestriction(false);
        guestPut.setFoodDescription(null);

        guestAccess = new GuestAccessDTO();
        guestAccess.setIdGuest(1L);
        guestAccess.setIdEvent(100L);
        guestAccess.setAccessType(AccessType.ENTRY);
        guestAccess.setActionDate(LocalDateTime.of(2025, 5, 30, 20, 0));
    }

    @Test
    @DisplayName("GC-001/Should return all guests successfully")
    void testGetAllGuestsSuccess() throws Exception {
        Page<GuestDTO> page = new PageImpl<>(List.of(guest));
        given(guestService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/guests")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Carla"));
    }

    @Test
    @DisplayName("GC-002/Should return guest by ID successfully")
    void testGetGuestByIdSuccess() throws Exception {
        given(guestService.findById(1L)).willReturn(Optional.of(guest));

        mockMvc.perform(get("/guests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Carla"));
    }

    @Test
    @DisplayName("GC-003/Should return 404 when guest is not found by ID")
    void testGetGuestByIdNotFound() throws Exception {
        given(guestService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/guests/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Invitado no encontrado"));
    }

    @Test
    @DisplayName("GC-004/Should create guest successfully")
    void testCreateGuestSuccess() throws Exception {
        given(guestService.save(any(GuestPostDTO.class))).willReturn(guest);

        mockMvc.perform(post("/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guestPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Carla"));
    }

    @Test
    @DisplayName("GC-005/Should return 400 when invalid guest post request")
    void testCreateGuestBadRequest() throws Exception {
        mockMvc.perform(post("/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GC-006/Should update guest successfully")
    void testUpdateGuestSuccess() throws Exception {
        given(guestService.update(eq(1L), any(GuestPutDTO.class))).willReturn(guest);

        mockMvc.perform(put("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guestPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Carla"));
    }

    @Test
    @DisplayName("GC-007/Should delete guest successfully")
    void testDeleteGuestSuccess() throws Exception {
        mockMvc.perform(delete("/guests/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GC-008/Should return guests by filters successfully")
    void testFilterGuestsSuccess() throws Exception {
        Page<GuestDTO> page = new PageImpl<>(List.of(guest));
        given(guestService.findByFilters(any(), eq("VIP"), eq(true), eq("Lopez"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/guests/filter")
                        .param("guestType", "VIP")
                        .param("isActive", "true")
                        .param("searchValue", "Lopez")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].firstName").value("Carla"));
    }

    @Test
    @DisplayName("GC-009/Should create guests in bulk for event")
    void testSaveGuestsToEventSuccess() throws Exception {
        List<GuestPostDTO> guestsToPost = List.of(guestPost);
        List<GuestDTO> guestsSaved = List.of(guest);

        given(guestService.saveGuestsToEvent(eq(guestsToPost), eq(100L))).willReturn(guestsSaved);

        mockMvc.perform(post("/guests/event/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guestsToPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].firstName").value("Carla"));
    }

    @Test
    @DisplayName("GC-010/Should get guests from an event")
    void testGetGuestsFromEvent() throws Exception {
        List<GuestDTO> guests = List.of(guest);
        given(guestService.getGuestFromEvent(100L)).willReturn(guests);

        mockMvc.perform(get("/guests/event/100"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].firstName").value("Carla"));
    }

    @Test
    @DisplayName("GC-011/Should register guest access successfully")
    void testRegisterGuestAccessSuccess() throws Exception {
        GuestAccessDTO accessDTO = new GuestAccessDTO();
        accessDTO.setIdGuest(1L);
        accessDTO.setIdEvent(100L);
        accessDTO.setAccessType(AccessType.ENTRY);
        accessDTO.setActionDate(LocalDateTime.of(2025, 5, 30, 20, 0));

        given(guestService.registerAccess(any(GuestAccessDTO.class))).willReturn(guest);

        mockMvc.perform(post("/guests/access")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accessDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Carla"));
    }
}

