package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.EventPostDTO;
import com.tup.ps.erpevents.dtos.event.EventPutDTO;
import com.tup.ps.erpevents.dtos.event.relations.EventsEmployeesDTO;
import com.tup.ps.erpevents.dtos.event.relations.EventsSuppliersDTO;
import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.entities.intermediates.EventsEmployeesEntity;
import com.tup.ps.erpevents.entities.intermediates.EventsSuppliersEntity;
import com.tup.ps.erpevents.enums.AmountStatus;
import com.tup.ps.erpevents.exceptions.ApiException;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.EventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private static final String[] EVENT_FIELDS = {
            "title", "description"
    };

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Qualifier("strictMapper")
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GenericSpecification<EventEntity> specification;
    @Autowired
    private EventsEmployeesRepository eventsEmployeesRepository;
    @Autowired
    private EventsSuppliersRepository eventsSuppliersRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        Page<EventEntity> page = eventRepository.findAllBySoftDelete(false, pageable);
        return page.map(this::mapEventEntityToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventDTO> findById(Long id) {
        return eventRepository.findByIdEventAndSoftDeleteFalse(id)
                .filter(event -> Boolean.FALSE.equals(event.getSoftDelete()))
                .map(this::mapEventEntityToDTO);
    }

    @Override
    @Transactional
    public EventDTO save(EventPostDTO dto) {

        EventEntity event = new EventEntity();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventType(dto.getEventType());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStatus(dto.getStatus());
        event.setCreationDate(LocalDateTime.now());
        event.setUpdateDate(LocalDateTime.now());
        event.setSoftDelete(false);

        if (dto.getEmployeeIds() != null) {
            List<EventsEmployeesEntity> employeeRelations = saveEmployeeRelations(dto, event);
            event.setEventEmployees(employeeRelations);
        }

        if (dto.getSupplierIds() != null) {
            List<EventsSuppliersEntity> supplierRelations = saveSupplierRelations(dto, event);
            event.setEventSuppliers(supplierRelations);
        }

        ClientEntity client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
        event.setClient(client);

        if (dto.getLocation() != null) {
            LocationEntity newLocation = modelMapper.map(dto.getLocation(), LocationEntity.class);
            LocationEntity savedLocation = locationRepository.save(newLocation);
            event.setLocation(savedLocation);
        } else {
            LocationEntity location = locationRepository.findById(dto.getLocationId())
                    .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ubicación no encontrada"));
            event.setLocation(location);
        }

        EventEntity savedEvent = eventRepository.save(event);

        if (dto.getTasks() != null && !dto.getTasks().isEmpty()) {
            List<TaskEntity> tasks = dto.getTasks().stream().map(taskDTO -> {
                TaskEntity task = new TaskEntity();
                task.setTitle(taskDTO.getTitle());
                task.setDescription(taskDTO.getDescription());
                task.setStatus(taskDTO.getStatus());
                task.setEvent(savedEvent);
                return task;
            }).toList();
            taskRepository.saveAll(tasks);
            savedEvent.setTasks(tasks);
        }

        return mapEventEntityToDTO(savedEvent);
    }


    @Override
    @Transactional
    public EventDTO update(Long id, EventPutDTO dto) {

        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventType(dto.getEventType());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setStatus(dto.getStatus());
        event.setUpdateDate(LocalDateTime.now());

        if (dto.getEmployeeIds() != null) {
            event.getEventEmployees().clear();
            List<EventsEmployeesEntity> employeeRelations = saveEmployeeRelations(dto, event);
            event.getEventEmployees().addAll(employeeRelations);
        }

        if (dto.getSupplierIds() != null) {
            event.getEventSuppliers().clear();
            List<EventsSuppliersEntity> supplierRelations = saveSupplierRelations(dto, event);
            event.getEventSuppliers().addAll(supplierRelations);
        }

        if (dto.getLocationId() != null) {
            LocationEntity location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Ubicación no encontrada"));
            event.setLocation(location);
        }

        EventEntity saved = eventRepository.save(event);

        return mapEventEntityToDTO(saved);
    }


    @Override
    public void delete(Long id) {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

        event.setSoftDelete(true);
        event.setUpdateDate(LocalDateTime.now());

        if (event.getTasks() != null) {
            event.getTasks().forEach(task -> {
                task.setSoftDelete(true);
                task.setUpdateDate(LocalDateTime.now());
            });

            taskRepository.saveAll(event.getTasks());
        }

        eventRepository.save(event);
    }


    @Override
    public Page<EventDTO> findByFilters(Pageable pageable,
                                        String eventType,
                                        String eventState,
                                        Boolean isActive,
                                        String searchValue,
                                        LocalDate creationStart,
                                        LocalDate creationEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (eventType != null) {
            filters.put("eventType", eventType);
        }

        if (eventState != null) {
            filters.put("eventState", eventState);
        }

        if (isActive != null) {
            filters.put("softDelete", !isActive);
        }

        Specification<EventEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<EventEntity> searchSpec = specification.valueDynamicFilter(searchValue, EVENT_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((creationStart == null) ^ (creationEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (creationStart != null && creationEnd != null) {
            if (creationStart.isAfter(creationEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "creationStart no puede ser mayor a creationEnd");
            }
            Specification<EventEntity> dateSpec = specification.filterBetween(creationStart, creationEnd, "creationDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return eventRepository.findAll(spec, pageable)
                .map(this::mapEventEntityToDTO);
    }


    private List<EventsEmployeesDTO> mapEventEmployees(List<EventsEmployeesEntity> eventEmployees) {
        if (eventEmployees == null) {
            return List.of();
        }
        return eventEmployees.stream()
                .map(rel -> {
                    EventsEmployeesDTO employeesDTO = modelMapper.map(rel, EventsEmployeesDTO.class);
                    employeesDTO.setIdEmployee(rel.getEmployee().getIdEmployee());
                    employeesDTO.setIdEvent(rel.getEvent().getIdEvent());
                    return employeesDTO;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<EventsSuppliersDTO> mapEventSuppliers(List<EventsSuppliersEntity> eventSuppliers) {
        if (eventSuppliers == null) {
            return List.of();
        }
        return eventSuppliers.stream()
                .map(rel -> {
                    EventsSuppliersDTO suppliersDTO = modelMapper.map(rel, EventsSuppliersDTO.class);
                    suppliersDTO.setIdSupplier(rel.getSupplier().getIdSupplier());
                    suppliersDTO.setIdEvent(rel.getEvent().getIdEvent());
                    return suppliersDTO;
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private List<Long> extractEmployeeIds(List<EventsEmployeesEntity> eventEmployees) {
        if (eventEmployees == null) {
            return List.of();
        }
        return eventEmployees.stream()
                .map(rel -> rel.getEmployee().getIdEmployee())
                .toList();
    }

    private List<Long> extractSupplierIds(List<EventsSuppliersEntity> eventSuppliers) {
        if (eventSuppliers == null) {
            return List.of();
        }
        return eventSuppliers.stream()
                .map(rel -> rel.getSupplier().getIdSupplier())
                .toList();
    }

    private EventDTO mapEventEntityToDTO(EventEntity event) {
        EventDTO dto = new EventDTO();
        dto.setIdEvent(event.getIdEvent());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setEventType(event.getEventType());
        dto.setStartDate(event.getStartDate());
        dto.setEndDate(event.getEndDate());
        dto.setStatus(event.getStatus());
        dto.setSoftDelete(event.getSoftDelete());
        dto.setClient(modelMapper.map(event.getClient(), ClientDTO.class));
        dto.setLocation(modelMapper.map(event.getLocation(), LocationDTO.class));
        dto.setCreationDate(event.getCreationDate());
        dto.setUpdateDate(event.getUpdateDate());

        dto.setEmployees(mapEventEmployees(event.getEventEmployees()));
        dto.setEmployeesIds(extractEmployeeIds(event.getEventEmployees()));

        dto.setGuests(event.getGuests() != null
                ? event.getGuests().stream().map(GuestEntity::getIdGuest).toList()
                : List.of());

        dto.setSuppliers(mapEventSuppliers(event.getEventSuppliers()));
        dto.setSuppliersIds(extractSupplierIds(event.getEventSuppliers()));

        dto.setTasks(event.getTasks() != null
                ? event.getTasks().stream().map(task -> modelMapper.map(task, TaskDTO.class)).toList()
                : List.of());

        return dto;
    }

    private List<EventsEmployeesEntity> saveEmployeeRelations(EventPutDTO dto, EventEntity event) {
        List<EventsEmployeesEntity> employeeRelations = dto.getEmployeeIds().stream()
                .map(id -> {
                    EmployeeEntity employee = employeeRepository.findById(id)
                            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
                    EventsEmployeesEntity relation = new EventsEmployeesEntity();
                    relation.setEmployee(employee);
                    relation.setEvent(event);
                    relation.setStatus(AmountStatus.DUE);
                    relation.setAmount(0.0);
                    relation.setBalance(0.0);
                    relation.setPayment("PENDING");
                    return relation;
                })
                .toList();
        return eventsEmployeesRepository.saveAll(employeeRelations);
    }

    private List<EventsEmployeesEntity> saveEmployeeRelations(EventPostDTO dto, EventEntity event) {
        List<EventsEmployeesEntity> employeeRelations = dto.getEmployeeIds().stream()
                .map(id -> {
                    EmployeeEntity employee = employeeRepository.findById(id)
                            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
                    EventsEmployeesEntity relation = new EventsEmployeesEntity();
                    relation.setEmployee(employee);
                    relation.setEvent(event);
                    relation.setStatus(AmountStatus.DUE);
                    relation.setAmount(0.0);
                    relation.setBalance(0.0);
                    relation.setPayment("PENDING");
                    return relation;
                })
                .toList();
        return eventsEmployeesRepository.saveAll(employeeRelations);
    }

    private List<EventsSuppliersEntity> saveSupplierRelations(EventPutDTO dto, EventEntity event) {
        List<EventsSuppliersEntity> supplierRelations = dto.getSupplierIds().stream()
                .map(idSupplier -> {
                    SupplierEntity supplier = supplierRepository.findById(idSupplier)
                            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
                    EventsSuppliersEntity relation = new EventsSuppliersEntity();
                    relation.setSupplier(supplier);
                    relation.setEvent(event);
                    relation.setStatus(AmountStatus.DUE);
                    relation.setAmount(0.0);
                    relation.setBalance(0.0);
                    relation.setPayment("PENDING");
                    return relation;
                })
                .toList();
        return eventsSuppliersRepository.saveAll(supplierRelations);
    }

    private List<EventsSuppliersEntity> saveSupplierRelations(EventPostDTO dto, EventEntity event) {
        List<EventsSuppliersEntity> supplierRelations = dto.getSupplierIds().stream()
                .map(idSupplier -> {
                    SupplierEntity supplier = supplierRepository.findById(idSupplier)
                            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
                    EventsSuppliersEntity relation = new EventsSuppliersEntity();
                    relation.setSupplier(supplier);
                    relation.setEvent(event);
                    relation.setStatus(AmountStatus.DUE);
                    relation.setAmount(0.0);
                    relation.setBalance(0.0);
                    relation.setPayment("PENDING");
                    return relation;
                })
                .toList();
        return eventsSuppliersRepository.saveAll(supplierRelations);
    }






}
