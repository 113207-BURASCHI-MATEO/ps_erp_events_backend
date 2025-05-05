package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.supplier.SupplierDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPostDTO;
import com.tup.ps.erpevents.dtos.supplier.SupplierPutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface SupplierService {

    /**
     * Obtiene todos los proveedores paginados.
     *
     * @param pageable información de paginación
     * @return página con proveedores
     */
    Page<SupplierDTO> findAll(Pageable pageable);

    /**
     * Busca un proveedor por ID.
     *
     * @param id ID del proveedor
     * @return proveedor encontrado o vacío si no existe
     */
    Optional<SupplierDTO> findById(Long id);

    /**
     * Crea un nuevo proveedor.
     *
     * @param supplierDTO datos del proveedor
     * @return proveedor creado
     */
    SupplierDTO save(SupplierPostDTO supplierDTO);

    /**
     * Actualiza un proveedor existente.
     *
     * @param id          ID del proveedor
     * @param supplierDTO nuevos datos del proveedor
     * @return proveedor actualizado
     */
    SupplierDTO update(Long id, SupplierPutDTO supplierDTO);

    /**
     * Realiza una baja lógica del proveedor (soft delete).
     *
     * @param id ID del proveedor
     */
    void delete(Long id);

    /**
     * Busca proveedores con filtros dinámicos.
     *
     * @param pageable          información de paginación
     * @param supplierType      tipo de proveedor
     * @param isActive          estado lógico (activo/inactivo)
     * @param searchValue       término de búsqueda general
     * @param creationStart     fecha inicial de creación
     * @param creationEnd       fecha final de creación
     * @return página con resultados filtrados
     */
    Page<SupplierDTO> findByFilters(Pageable pageable,
                                    String supplierType,
                                    Boolean isActive,
                                    String searchValue,
                                    LocalDate creationStart,
                                    LocalDate creationEnd);
}

