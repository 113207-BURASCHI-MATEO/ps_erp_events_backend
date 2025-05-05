package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.location.LocationPostDTO;
import com.tup.ps.erpevents.dtos.location.LocationPutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface LocationService {

    /**
     * Obtiene todas las ubicaciones paginadas.
     *
     * @param pageable información de paginación
     * @return página con ubicaciones
     */
    Page<LocationDTO> findAll(Pageable pageable);

    /**
     * Busca una ubicación por ID.
     *
     * @param id ID de la ubicación
     * @return ubicación encontrada o vacío si no existe
     */
    Optional<LocationDTO> findById(Long id);

    /**
     * Crea una nueva ubicación.
     *
     * @param locationDTO datos de la ubicación
     * @return ubicación creada
     */
    LocationDTO save(LocationPostDTO locationDTO);

    /**
     * Actualiza una ubicación existente.
     *
     * @param id           ID de la ubicación
     * @param locationDTO  nuevos datos de la ubicación
     * @return ubicación actualizada
     */
    LocationDTO update(Long id, LocationPutDTO locationDTO);

    /**
     * Realiza una baja lógica de la ubicación.
     *
     * @param id ID de la ubicación
     */
    void delete(Long id);

    /**
     * Busca ubicaciones con filtros dinámicos.
     *
     * @param pageable      información de paginación
     * @param isActive      estado lógico
     * @param searchValue   término de búsqueda general
     * @param creationStart fecha inicial
     * @param creationEnd   fecha final
     * @return página con ubicaciones filtradas
     */
    Page<LocationDTO> findByFilters(Pageable pageable,
                                    Boolean isActive,
                                    String searchValue,
                                    LocalDate creationStart,
                                    LocalDate creationEnd);
}

