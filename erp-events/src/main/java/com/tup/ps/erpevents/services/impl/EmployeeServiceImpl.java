package com.tup.ps.erpevents.services.impl;

import com.tup.ps.erpevents.dtos.employee.EmployeeDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePostDTO;
import com.tup.ps.erpevents.dtos.employee.EmployeePutDTO;
import com.tup.ps.erpevents.dtos.user.UserRegisterDTO;
import com.tup.ps.erpevents.entities.EmployeeEntity;
import com.tup.ps.erpevents.entities.UserEntity;
import com.tup.ps.erpevents.enums.RoleName;
import com.tup.ps.erpevents.repositories.EmployeeRepository;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.EmployeeService;
import com.tup.ps.erpevents.services.SecurityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final String[] EMPLOYEE_FIELDS = {
            "firstName", "lastName", "documentNumber", "email", "cuit"
    };

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Especificaciones dinámicas para filtros complejos.
     */
    @Autowired
    private GenericSpecification<EmployeeEntity> specification;

    @Override
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        return employeeRepository.findAllBySoftDelete(false, pageable)
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public Optional<EmployeeDTO> findById(Long id) {
        return employeeRepository.findById(id)
                .filter(employee -> Boolean.FALSE.equals(employee.getSoftDelete()))
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }

    @Override
    public EmployeeDTO save(EmployeePostDTO dto) {

        UserRegisterDTO userRegisterDTO = modelMapper.map(dto, UserRegisterDTO.class);
        UserEntity savedUser = securityService.registerEntity(userRegisterDTO, RoleName.EMPLOYEE);

        EmployeeEntity employee = modelMapper.map(dto, EmployeeEntity.class);
        employee.setUser(savedUser);
        employee.setSoftDelete(false);

        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeePutDTO dto) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        modelMapper.map(dto, employee);

        return modelMapper.map(employeeRepository.save(employee), EmployeeDTO.class);
    }

    @Override
    public void delete(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        Long userId = employee.getUser().getIdUser();
        userServiceImpl.softDeleteUser(userId, employee.getEmail());

        employee.setSoftDelete(true);
        employeeRepository.save(employee);
    }

    @Override
    public Page<EmployeeDTO> findByFilters(Pageable pageable,
                                           String documentType,
                                           Boolean isActive,
                                           String searchValue,
                                           LocalDate birthdateStart,
                                           LocalDate birthdateEnd) {

        Map<String, Object> filters = new HashMap<>();

        if (documentType != null) {
            filters.put("documentType", documentType);
        }
        if (isActive != null) {
            filters.put("softDelete", !isActive); // true → eliminado
        }

        Specification<EmployeeEntity> spec = specification.dynamicFilter(filters);

        if (searchValue != null) {
            Specification<EmployeeEntity> searchSpec =
                    specification.valueDynamicFilter(searchValue, EMPLOYEE_FIELDS);
            spec = Specification.where(spec).and(searchSpec);
        }

        if ((birthdateStart == null) ^ (birthdateEnd == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ambas fechas son requeridas");
        } else if (birthdateStart != null && birthdateEnd != null) {
            if (birthdateStart.isAfter(birthdateEnd)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "birthdateStart no puede ser mayor a birthdateEnd");
            }
            Specification<EmployeeEntity> dateSpec = specification.filterBetween(
                    birthdateStart, birthdateEnd, "birthDate");
            spec = Specification.where(spec).and(dateSpec);
        }

        return employeeRepository.findAll(spec, pageable)
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));
    }
}
