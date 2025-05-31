package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPutDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.entities.intermediates.EventsGuestsEntity;
import com.tup.ps.erpevents.enums.*;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.GuestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {

    @Mock private GuestRepository guestRepository;
    @Mock private EventRepository eventRepository;
    @Mock private EventsGuestsRepository eventsGuestsRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private GenericSpecification<GuestEntity> specification;

    @InjectMocks private GuestServiceImpl guestService;

    private GuestEntity guest;
    private GuestDTO guestDTO;
    private GuestPostDTO guestPostDTO;
    private GuestPutDTO guestPutDTO;
    private EventEntity event;
    private EventsGuestsEntity relation;

    @BeforeEach
    void setUp() {
        guest = new GuestEntity();
        guest.setIdGuest(1L);
        guest.setFirstName("John");
        guest.setLastName("Doe");
        guest.setEmail("john@example.com");
        guest.setSoftDelete(false);

        guestDTO = new GuestDTO();
        guestDTO.setIdGuest(1L);

        guestPostDTO = new GuestPostDTO();
        guestPostDTO.setFirstName("John");
        guestPostDTO.setLastName("Doe");
        guestPostDTO.setEmail("john@example.com");
        guestPostDTO.setIdEvent(1L);

        guestPutDTO = new GuestPutDTO();
        guestPutDTO.setIdEvent(1L);

        event = new EventEntity();
        event.setIdEvent(1L);

        relation = new EventsGuestsEntity();
        relation.setEvent(event);
        relation.setGuest(guest);
        relation.setNote("Test note");
        relation.setType(GuestType.VIP);
    }

    @Test
    @DisplayName("GU-001/Should return guest by ID")
    void testFindById() {
        guest.setGuestEvents(List.of(relation));
        given(guestRepository.findById(1L)).willReturn(Optional.of(guest));
        given(modelMapper.map(eq(guest), eq(GuestDTO.class))).willReturn(guestDTO);

        Optional<GuestDTO> result = guestService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdGuest());
    }

    @Test
    @DisplayName("GU-002/Should save new guest and relation")
    void testSaveNewGuest() {
        given(eventRepository.findById(1L)).willReturn(Optional.of(event));
        given(guestRepository.findByFirstNameAndLastNameAndEmail(any(), any(), any())).willReturn(Optional.empty());
        given(modelMapper.map(eq(guestPostDTO), eq(GuestEntity.class))).willReturn(guest);
        given(guestRepository.save(any())).willReturn(guest);
        given(eventsGuestsRepository.save(any())).willReturn(relation);
        given(modelMapper.map(eq(guest), eq(GuestDTO.class))).willReturn(guestDTO);

        GuestDTO result = guestService.save(guestPostDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("GU-003/Should update existing guest and relation")
    void testUpdateGuest() {
        guest.setGuestEvents(List.of(relation));
        given(guestRepository.findById(1L)).willReturn(Optional.of(guest));
        given(eventRepository.findById(1L)).willReturn(Optional.of(event));
        given(eventsGuestsRepository.save(any())).willReturn(relation);
        willDoNothing().given(modelMapper).map(eq(guestPutDTO), eq(guest));
        given(guestRepository.save(any())).willReturn(guest);
        given(modelMapper.map(eq(guest), eq(GuestDTO.class))).willReturn(guestDTO);

        GuestDTO result = guestService.update(1L, guestPutDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("GU-004/Should delete guest")
    void testDeleteGuest() {
        given(guestRepository.findById(1L)).willReturn(Optional.of(guest));

        assertDoesNotThrow(() -> guestService.delete(1L));
        assertTrue(guest.getSoftDelete());
    }

    @Test
    @DisplayName("GU-005/Should throw when only one creation date is provided")
    void testFindByFiltersInvalidDates() {
        assertThrows(ResponseStatusException.class, () ->
                guestService.findByFilters(Pageable.unpaged(), null, null, null, LocalDate.now(), null));
    }
}

