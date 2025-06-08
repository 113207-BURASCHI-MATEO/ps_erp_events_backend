package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.EventPostDTO;
import com.tup.ps.erpevents.dtos.event.EventPutDTO;
import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.task.TaskEventPostDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.entities.intermediates.EventsEmployeesEntity;
import com.tup.ps.erpevents.entities.intermediates.EventsSuppliersEntity;
import com.tup.ps.erpevents.enums.AmountStatus;
import com.tup.ps.erpevents.enums.EventStatus;
import com.tup.ps.erpevents.enums.EventType;
import com.tup.ps.erpevents.enums.TaskStatus;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.EventServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @Mock private EventRepository eventRepository;
    @Mock private EmployeeRepository employeeRepository;
    @Mock private SupplierRepository supplierRepository;
    @Mock private TaskRepository taskRepository;
    @Mock private ClientRepository clientRepository;
    @Mock private LocationRepository locationRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private GenericSpecification<EventEntity> specification;
    @Mock private EventsEmployeesRepository eventsEmployeesRepository;
    @Mock private EventsSuppliersRepository eventsSuppliersRepository;
    @Mock private AccountRespository accountRespository;

    @InjectMocks private EventServiceImpl eventService;

    private EventPostDTO eventPostDTO;
    private EventPutDTO eventPutDTO;
    private EventEntity eventEntity;
    private EventDTO eventDTO;
    private LocationEntity locationEntity;
    private ClientEntity clientEntity;
    private AccountEntity accountEntity;

    @BeforeEach
    void setUp() {
        accountEntity = new AccountEntity();
        accountEntity.setIdAccount(1L);
        accountEntity.setBalance(BigDecimal.TEN);

        eventPostDTO = new EventPostDTO();
        eventPostDTO.setTitle("Test Event");
        eventPostDTO.setDescription("Descripción");
        eventPostDTO.setEventType(EventType.SOCIAL);
        eventPostDTO.setStartDate(LocalDateTime.now());
        eventPostDTO.setEndDate(LocalDateTime.now().plusDays(1));
        eventPostDTO.setStatus(EventStatus.CONFIRMED);
        eventPostDTO.setClientId(1L);
        eventPostDTO.setLocationId(1L);

        eventPutDTO = new EventPutDTO();
        eventPutDTO.setTitle("Evento Actualizado");
        eventPutDTO.setDescription("Nueva descripción");
        eventPutDTO.setEventType(EventType.SOCIAL);
        eventPutDTO.setStartDate(LocalDateTime.now());
        eventPutDTO.setEndDate(LocalDateTime.now().plusDays(2));
        eventPutDTO.setStatus(EventStatus.SUSPENDED);
        eventPutDTO.setLocationId(1L);

        eventEntity = new EventEntity();
        eventEntity.setIdEvent(1L);
        eventEntity.setTitle("Test Event");
        eventEntity.setEventEmployees(new ArrayList<>());
        eventEntity.setEventSuppliers(new ArrayList<>());
        eventEntity.setAccount(accountEntity);

        eventDTO = new EventDTO();
        eventDTO.setIdEvent(1L);
        eventDTO.setTitle("Test Event");

        locationEntity = new LocationEntity();
        locationEntity.setIdLocation(1L);

        clientEntity = new ClientEntity();
        clientEntity.setIdClient(1L);


    }

    @Test
    @DisplayName("EV-001/Should return all events")
    void testFindAll() {
        Page<EventEntity> page = new PageImpl<>(List.of(eventEntity));
        given(eventRepository.findAllBySoftDelete(false, Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(), eq(ClientDTO.class))).willReturn(new ClientDTO());
        given(modelMapper.map(any(), eq(LocationDTO.class))).willReturn(new LocationDTO());

        Page<EventDTO> result = eventService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("EV-002/Should find event by ID")
    void testFindById() {
        given(eventRepository.findByIdEventAndSoftDeleteFalse(1L)).willReturn(Optional.of(eventEntity));
        given(modelMapper.map(any(), eq(ClientDTO.class))).willReturn(new ClientDTO());
        given(modelMapper.map(any(), eq(LocationDTO.class))).willReturn(new LocationDTO());

        Optional<EventDTO> result = eventService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Event", result.get().getTitle());
    }

    @Test
    @DisplayName("EV-003/Should throw when only one creation date provided")
    void testFindByFiltersOneDate() {
        assertThrows(ResponseStatusException.class, () ->
                eventService.findByFilters(Pageable.unpaged(), null,null, null, null, null, LocalDate.now()));
    }

    @Test
    @DisplayName("EV-004/Should throw when creationStart is after creationEnd")
    void testFindByFiltersInvalidDateRange() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.minusDays(1);

        assertThrows(ResponseStatusException.class, () ->
                eventService.findByFilters(Pageable.unpaged(), null, null, null, null, null, end));
    }

    @Test
    @DisplayName("EV-005/Should change event status successfully")
    void testEventStatusUpdate() {
        eventEntity.setStatus(EventStatus.CONFIRMED);
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));
        given(eventRepository.save(any())).willReturn(eventEntity);
        given(modelMapper.map(any(), eq(EventDTO.class))).willReturn(eventDTO);

        EventDTO result = eventService.eventStatus(1L, EventStatus.SUSPENDED);

        assertEquals("Test Event", result.getTitle());
    }

    @Test
    @DisplayName("EV-006/Should throw when invalid event status transition")
    void testInvalidEventStatusTransition() {
        eventEntity.setStatus(EventStatus.FINISHED);
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));

        assertThrows(ResponseStatusException.class, () ->
                eventService.eventStatus(1L, EventStatus.SUSPENDED));
    }

    @Test
    @DisplayName("EV-007/Should throw when event not found")
    void testEventNotFound() {
        given(eventRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> eventService.update(1L, new EventPutDTO()));
        assertThrows(EntityNotFoundException.class, () -> eventService.delete(1L));
        assertThrows(EntityNotFoundException.class, () -> eventService.eventStatus(1L, EventStatus.CONFIRMED));
    }

    @Test
    @DisplayName("EV-008/Should throw when saving event with missing client")
    void testSaveEventWithMissingClient() {
        eventPostDTO.setClient(null);
        given(clientRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ApiException.class, () -> eventService.save(eventPostDTO));
    }

    @Test
    @DisplayName("EV-009/Should throw when saving event with missing location")
    void testSaveEventWithMissingLocation() {
        eventPostDTO.setLocation(null);
        given(clientRepository.findById(anyLong())).willReturn(Optional.of(clientEntity));
        given(locationRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ApiException.class, () -> eventService.save(eventPostDTO));
    }

    @Test
    @DisplayName("EV-010/Should throw when updating event with missing location")
    void testUpdateEventWithMissingLocation() {
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));
        given(locationRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(ApiException.class, () -> eventService.update(1L, eventPutDTO));
    }

    @Test
    @DisplayName("EV-011/Should save event with client and location")
    void testSaveEvent() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        given(locationRepository.findById(1L)).willReturn(Optional.of(locationEntity));
        given(eventRepository.save(any(EventEntity.class))).willReturn(eventEntity);
        given(accountRespository.save(any(AccountEntity.class))).willReturn(accountEntity);


        EventDTO result = eventService.save(eventPostDTO);

        assertNotNull(result);
        assertEquals("Test Event", result.getTitle());
    }

    @Test
    @DisplayName("EV-012/Should update existing event")
    void testUpdateEvent() {
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));
        given(locationRepository.findById(1L)).willReturn(Optional.of(locationEntity));
        given(eventRepository.save(any(EventEntity.class))).willReturn(eventEntity);

        EventDTO result = eventService.update(1L, eventPutDTO);

        assertNotNull(result);
        assertEquals("Evento Actualizado", result.getTitle());
    }

    @Test
    @DisplayName("EV-013/Should save event with employee relations")
    void testSaveEventWithEmployees() {
        eventPostDTO.setEmployeeIds(List.of(1L));
        given(employeeRepository.findById(1L)).willReturn(Optional.of(new EmployeeEntity()));
        given(eventsEmployeesRepository.saveAll(any())).willReturn(List.of(new EventsEmployeesEntity()));
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        given(locationRepository.findById(1L)).willReturn(Optional.of(locationEntity));
        given(eventRepository.save(any(EventEntity.class))).willReturn(eventEntity);
        given(accountRespository.save(any(AccountEntity.class))).willReturn(accountEntity);

        EventDTO result = eventService.save(eventPostDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("EV-014/Should save event with supplier relations")
    void testSaveEventWithSuppliers() {
        eventPostDTO.setSupplierIds(List.of(1L));
        given(supplierRepository.findById(1L)).willReturn(Optional.of(new SupplierEntity()));
        given(eventsSuppliersRepository.saveAll(any())).willReturn(List.of(new EventsSuppliersEntity()));
        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        given(locationRepository.findById(1L)).willReturn(Optional.of(locationEntity));
        given(eventRepository.save(any(EventEntity.class))).willReturn(eventEntity);
        given(accountRespository.save(any(AccountEntity.class))).willReturn(accountEntity);

        EventDTO result = eventService.save(eventPostDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("EV-015/Should save event with tasks")
    void testSaveEventWithTasks() {
        TaskEventPostDTO task = new TaskEventPostDTO();
        task.setTitle("Task 1");
        task.setDescription("Description");
        task.setStatus(TaskStatus.PENDING);
        eventPostDTO.setTasks(List.of(task));

        given(clientRepository.findById(1L)).willReturn(Optional.of(clientEntity));
        given(locationRepository.findById(1L)).willReturn(Optional.of(locationEntity));
        given(eventRepository.save(any(EventEntity.class))).willReturn(eventEntity);
        given(taskRepository.saveAll(any())).willReturn(List.of(new TaskEntity()));
        given(accountRespository.save(any(AccountEntity.class))).willReturn(accountEntity);

        EventDTO result = eventService.save(eventPostDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("EV-016/Should update event with employee relations")
    void testUpdateEventWithEmployees() {
        eventPutDTO.setEmployeeIds(List.of(1L));
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));

        EmployeeEntity employee = new EmployeeEntity();
        employee.setIdEmployee(1L);
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        EventsEmployeesEntity employeeRel = new EventsEmployeesEntity();
        employeeRel.setEmployee(employee);
        employeeRel.setEvent(eventEntity);
        employeeRel.setAmount(0.0);
        employeeRel.setBalance(0.0);
        employeeRel.setPayment("PENDING");
        employeeRel.setStatus(AmountStatus.DUE);

        List<EventsEmployeesEntity> relList = new ArrayList<>();
        relList.add(employeeRel);

        given(eventsEmployeesRepository.saveAll(any())).willReturn(relList);

        eventEntity.setClient(clientEntity);
        eventEntity.setLocation(locationEntity);
        eventEntity.setEventEmployees(relList);

        given(locationRepository.findById(1L)).willReturn(Optional.of(locationEntity));
        given(eventRepository.save(any(EventEntity.class))).willReturn(eventEntity);
        given(modelMapper.map(eq(clientEntity), eq(ClientDTO.class))).willReturn(new ClientDTO());
        given(modelMapper.map(eq(locationEntity), eq(LocationDTO.class))).willReturn(new LocationDTO());

        EventDTO result = eventService.update(1L, eventPutDTO);

        assertNotNull(result);
    }

    @Test
    @DisplayName("EV-017/Should update event with supplier relations")
    void testUpdateEventWithSuppliers() {
        eventPutDTO.setSupplierIds(List.of(1L));
        given(eventRepository.findById(1L)).willReturn(Optional.of(eventEntity));

        SupplierEntity supplier = new SupplierEntity();
        supplier.setIdSupplier(1L);
        given(supplierRepository.findById(1L)).willReturn(Optional.of(supplier));

        EventsSuppliersEntity supplierRel = new EventsSuppliersEntity();
        supplierRel.setSupplier(supplier);
        supplierRel.setEvent(eventEntity);
        supplierRel.setAmount(0.0);
        supplierRel.setBalance(0.0);
        supplierRel.setPayment("PENDING");
        supplierRel.setStatus(AmountStatus.DUE);

        List<EventsSuppliersEntity> relList = new ArrayList<>();
        relList.add(supplierRel);

        given(eventsSuppliersRepository.saveAll(any())).willReturn(relList);

        eventEntity.setClient(clientEntity);
        eventEntity.setLocation(locationEntity);
        eventEntity.setEventSuppliers(relList);

        given(locationRepository.findById(1L)).willReturn(Optional.of(locationEntity));
        given(eventRepository.save(any(EventEntity.class))).willReturn(eventEntity);
        given(modelMapper.map(eq(clientEntity), eq(ClientDTO.class))).willReturn(new ClientDTO());
        given(modelMapper.map(eq(locationEntity), eq(LocationDTO.class))).willReturn(new LocationDTO());

        EventDTO result = eventService.update(1L, eventPutDTO);

        assertNotNull(result);
    }
}

