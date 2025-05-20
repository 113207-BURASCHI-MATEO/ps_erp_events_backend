package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.event.EventDTO;
import com.tup.ps.erpevents.dtos.event.EventPostDTO;
import com.tup.ps.erpevents.dtos.event.EventPutDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public interface EventService {

    /**
     * Obtiene todos los eventos paginados.
     *
     * @param pageable información de paginación
     * @return página con eventos
     */
    Page<EventDTO> findAll(Pageable pageable);

    /**
     * Busca un evento por ID.
     *
     * @param id ID del evento
     * @return evento encontrado o vacío si no existe
     */
    Optional<EventDTO> findById(Long id);

    /**
     * Crea un nuevo evento.
     *
     * @param eventPostDTO datos del evento
     * @return evento creado
     */
    EventDTO save(EventPostDTO eventPostDTO);

    /**
     * Actualiza un evento existente.
     *
     * @param id            ID del evento
     * @param eventPutDTO   nuevos datos del evento
     * @return evento actualizado
     */
    EventDTO update(Long id, EventPutDTO eventPutDTO);

    /**
     * Realiza una baja lógica del evento (soft delete).
     *
     * @param id ID del evento
     */
    void delete(Long id);

    /**
     * Busca eventos con filtros dinámicos.
     *
     * @param pageable      información de paginación
     * @param eventType     tipo de evento
     * @param status        estado del evento
     * @param isActive      estado lógico (activo/inactivo)
     * @param searchValue   término de búsqueda general
     * @param dateStart     fecha de inicio del evento
     * @param dateEnd       fecha de fin del evento
     * @return página con resultados filtrados
     */
    Page<EventDTO> findByFilters(Pageable pageable,
                                 String eventType,
                                 String status,
                                 Boolean isActive,
                                 String searchValue,
                                 LocalDate dateStart,
                                 LocalDate dateEnd);

}

