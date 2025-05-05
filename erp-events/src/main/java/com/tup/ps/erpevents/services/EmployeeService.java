package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.employee.EmployeeDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePostDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public interface EmployeeService {

    /**
     * Obtiene todos los empleados paginados.
     *
     * @param pageable información de paginación
     * @return página con empleados
     */
    Page<EmployeeDTO> findAll(Pageable pageable);

    /**
     * Busca un empleado por ID.
     *
     * @param id ID del empleado
     * @return empleado encontrado o vacío si no existe
     */
    Optional<EmployeeDTO> findById(Long id);

    /**
     * Crea un nuevo empleado y su usuario asociado.
     *
     * @param employeeDTO datos del empleado
     * @return empleado creado
     */
    EmployeeDTO save(EmployeePostDTO employeeDTO);

    /**
     * Actualiza un empleado existente.
     *
     * @param id           ID del empleado
     * @param employeeDTO  nuevos datos del empleado
     * @return empleado actualizado
     */
    EmployeeDTO update(Long id, EmployeePutDTO employeeDTO);

    /**
     * Realiza una baja lógica del empleado (soft delete).
     *
     * @param id ID del empleado
     */
    void delete(Long id);

    Page<EmployeeDTO> findByFilters(Pageable pageable,
                                    String documentType,
                                    Boolean isActive,
                                    String searchValue,
                                    LocalDate birthdateStart,
                                    LocalDate birthdateEnd);
}