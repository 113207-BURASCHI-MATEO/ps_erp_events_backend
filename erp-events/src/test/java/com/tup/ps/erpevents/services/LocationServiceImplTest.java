package com.tup.ps.erpevents.services;

import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.location.LocationPostDTO;
import com.tup.ps.erpevents.dtos.location.LocationPutDTO;
import com.tup.ps.erpevents.entities.*;
import com.tup.ps.erpevents.repositories.*;
import com.tup.ps.erpevents.repositories.specs.GenericSpecification;
import com.tup.ps.erpevents.services.impl.LocationServiceImpl;
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
public class LocationServiceImplTest {

    @Mock private LocationRepository locationRepository;
    @Mock private GenericSpecification<LocationEntity> specification;
    @Mock private ModelMapper modelMapper;

    @InjectMocks private LocationServiceImpl locationService;

    private LocationEntity location;
    private LocationDTO locationDTO;
    private LocationPostDTO locationPostDTO;
    private LocationPutDTO locationPutDTO;

    @BeforeEach
    void setUp() {
        location = new LocationEntity();
        location.setIdLocation(1L);
        location.setSoftDelete(false);

        locationDTO = new LocationDTO();
        locationDTO.setIdLocation(1L);

        locationPostDTO = new LocationPostDTO();
        locationPutDTO = new LocationPutDTO();
    }

    @Test
    @DisplayName("LOC-001/Should return all locations")
    void testFindAll() {
        Page<LocationEntity> page = new PageImpl<>(List.of(location));
        given(locationRepository.findAllBySoftDelete(false, Pageable.unpaged())).willReturn(page);
        given(modelMapper.map(any(LocationEntity.class), eq(LocationDTO.class))).willReturn(locationDTO);

        Page<LocationDTO> result = locationService.findAll(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }

    @Test
    @DisplayName("LOC-002/Should find location by ID")
    void testFindById() {
        given(locationRepository.findById(1L)).willReturn(Optional.of(location));
        given(modelMapper.map(any(LocationEntity.class), eq(LocationDTO.class))).willReturn(locationDTO);

        Optional<LocationDTO> result = locationService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdLocation());
    }

    @Test
    @DisplayName("LOC-003/Should save new location")
    void testSaveLocation() {
        given(modelMapper.map(locationPostDTO, LocationEntity.class)).willReturn(location);
        given(locationRepository.save(location)).willReturn(location);
        given(modelMapper.map(location, LocationDTO.class)).willReturn(locationDTO);

        LocationDTO result = locationService.save(locationPostDTO);

        assertNotNull(result);
        assertEquals(1L, result.getIdLocation());
    }

    @Test
    @DisplayName("LOC-004/Should update existing location")
    void testUpdateLocation() {
        given(locationRepository.findById(1L)).willReturn(Optional.of(location));
        willDoNothing().given(modelMapper).map(locationPutDTO, location);
        given(locationRepository.save(location)).willReturn(location);
        given(modelMapper.map(location, LocationDTO.class)).willReturn(locationDTO);

        LocationDTO result = locationService.update(1L, locationPutDTO);

        assertNotNull(result);
        assertEquals(1L, result.getIdLocation());
    }

    @Test
    @DisplayName("LOC-005/Should delete existing location")
    void testDeleteLocation() {
        given(locationRepository.findById(1L)).willReturn(Optional.of(location));

        assertDoesNotThrow(() -> locationService.delete(1L));
        assertTrue(location.getSoftDelete());
    }

    @Test
    @DisplayName("LOC-006/Should throw when only one creation date provided")
    void testFindByFiltersOneDate() {
        assertThrows(ResponseStatusException.class, () ->
                locationService.findByFilters(Pageable.unpaged(), null, null, null, LocalDate.now()));
    }

    @Test
    @DisplayName("LOC-007/Should throw when creationStart is after creationEnd")
    void testFindByFiltersInvalidDateRange() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.minusDays(1);

        assertThrows(ResponseStatusException.class, () ->
                locationService.findByFilters(Pageable.unpaged(), null, null, start, end));
    }
}

