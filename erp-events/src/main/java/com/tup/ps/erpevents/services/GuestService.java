package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.guest.GuestDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPostDTO;
import com.tup.ps.erpevents.dtos.guest.GuestPutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public interface GuestService {

    /**
     * Obtiene todos los invitados paginados.
     *
     * @param pageable información de paginación
     * @return página con invitados
     */
    Page<GuestDTO> findAll(Pageable pageable);

    /**
     * Busca un invitado por ID.
     *
     * @param id ID del invitado
     * @return invitado encontrado o vacío si no existe
     */
    Optional<GuestDTO> findById(Long id);

    /**
     * Crea un nuevo invitado.
     *
     * @param guestDTO datos del invitado
     * @return invitado creado
     */
    GuestDTO save(GuestPostDTO guestDTO);

    /**
     * Actualiza un invitado existente.
     *
     * @param id       ID del invitado
     * @param guestDTO nuevos datos del invitado
     * @return invitado actualizado
     */
    GuestDTO update(Long id, GuestPutDTO guestDTO);

    /**
     * Realiza una baja lógica del invitado (soft delete).
     *
     * @param id ID del invitado
     */
    void delete(Long id);

    /**
     * Busca invitados con filtros dinámicos.
     *
     * @param pageable          información de paginación
     * @param guestType         tipo de invitado
     * @param isActive          estado lógico (activo/inactivo)
     * @param searchValue       término de búsqueda general
     * @param creationStart     fecha inicial de creación
     * @param creationEnd       fecha final de creación
     * @return página con resultados filtrados
     */
    Page<GuestDTO> findByFilters(Pageable pageable,
                                 String guestType,
                                 Boolean isActive,
                                 String searchValue,
                                 LocalDate creationStart,
                                 LocalDate creationEnd);

    List<GuestDTO> saveGuestsToEvent(List<GuestPostDTO> guestDTOList, Long idEvent);

    List<GuestDTO> getGuestFromEvent(Long idEvent);
}

