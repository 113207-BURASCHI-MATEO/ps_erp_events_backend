package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.client.ClientPutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
@Service
public interface ClientService {

    /**
     * Obtiene todos los clientes paginados.
     *
     * @param pageable información de paginación
     * @return página con clientes
     */
    Page<ClientDTO> findAll(Pageable pageable);

    /**
     * Busca un cliente por ID.
     *
     * @param id ID del cliente
     * @return cliente encontrado o vacío si no existe
     */
    Optional<ClientDTO> findById(Long id);

    /**
     * Crea un nuevo cliente.
     *
     * @param clientDTO datos del cliente
     * @return cliente creado
     */
    ClientDTO save(ClientPostDTO clientDTO);

    /**
     * Actualiza un cliente existente.
     *
     * @param id         ID del cliente
     * @param clientDTO  nuevos datos del cliente
     * @return cliente actualizado
     */
    ClientDTO update(Long id, ClientPutDTO clientDTO);

    /**
     * Realiza una baja lógica del cliente (soft delete).
     *
     * @param id ID del cliente
     */
    void delete(Long id);

    /**
     * Busca clientes con filtros dinámicos.
     *
     * @param pageable          información de paginación
     * @param isActive          estado lógico (activo/inactivo)
     * @param searchValue       término de búsqueda general
     * @param creationStart     fecha inicial de creación
     * @param creationEnd       fecha final de creación
     * @return página con resultados filtrados
     */
    Page<ClientDTO> findByFilters(Pageable pageable,
                                  Boolean isActive,
                                  String searchValue,
                                  LocalDate creationStart,
                                  LocalDate creationEnd);
}

