package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.location.LocationDTO;
import com.tup.ps.erpevents.dtos.location.LocationPostDTO;
import com.tup.ps.erpevents.dtos.location.LocationPutDTO;
import com.tup.ps.erpevents.enums.*;
import com.tup.ps.erpevents.services.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@test.com", roles = {"ADMIN"})
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LocationService locationService;

    private LocationDTO location;
    private LocationPostDTO locationPost;
    private LocationPutDTO locationPut;

    @BeforeEach
    void setUp() {
        location = new LocationDTO();
        location.setIdLocation(1L);
        location.setFantasyName("Salón El Castillo");
        location.setStreetAddress("Av. Siempre Viva");
        location.setNumber(742);
        location.setCity("Córdoba");
        location.setProvince(Province.CORDOBA);
        location.setCountry(Country.ARGENTINA);
        location.setPostalCode(5000);
        location.setLatitude(-31.4167);
        location.setLongitude(-64.1833);
        location.setCreationDate(LocalDateTime.now());
        location.setUpdateDate(LocalDateTime.now());
        location.setSoftDelete(false);

        locationPost = new LocationPostDTO();
        locationPost.setFantasyName("Salón El Castillo");
        locationPost.setStreetAddress("Av. Siempre Viva");
        locationPost.setNumber(742);
        locationPost.setCity("Córdoba");
        locationPost.setProvince(Province.CORDOBA);
        locationPost.setCountry(Country.ARGENTINA);
        locationPost.setPostalCode(5000);
        locationPost.setLatitude("-31.4167");
        locationPost.setLongitude("-64.1833");

        locationPut = new LocationPutDTO();
        locationPut.setIdLocation(1L);
        locationPut.setFantasyName("Salón El Castillo");
        locationPut.setStreetAddress("Av. Siempre Viva");
        locationPut.setNumber(742);
        locationPut.setCity("Córdoba");
        locationPut.setProvince(Province.CORDOBA);
        locationPut.setCountry(Country.ARGENTINA);
        locationPut.setPostalCode(5000);
        locationPut.setLatitude("-31.4167");
        locationPut.setLongitude("-64.1833");
    }

    @Test
    void testGetAllLocationsSuccess() throws Exception {
        Page<LocationDTO> page = new PageImpl<>(List.of(location));
        given(locationService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/locations")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fantasyName").value("Salón El Castillo"));
    }

    @Test
    void testGetLocationByIdSuccess() throws Exception {
        given(locationService.findById(1L)).willReturn(Optional.of(location));

        mockMvc.perform(get("/locations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fantasyName").value("Salón El Castillo"));
    }

    @Test
    void testGetLocationByIdNotFound() throws Exception {
        given(locationService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/locations/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Ubicación no encontrada"));
    }

    @Test
    void testCreateLocationSuccess() throws Exception {
        given(locationService.save(any(LocationPostDTO.class))).willReturn(location);

        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fantasyName").value("Salón El Castillo"));
    }

    @Test
    void testCreateLocationBadRequest() throws Exception {
        locationPost.setFantasyName(""); // Violando @NotBlank

        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationPost)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateLocationSuccess() throws Exception {
        given(locationService.update(eq(1L), any(LocationPutDTO.class))).willReturn(location);

        mockMvc.perform(put("/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fantasyName").value("Salón El Castillo"));
    }

    @Test
    void testDeleteLocationSuccess() throws Exception {
        mockMvc.perform(delete("/locations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFilterLocationsSuccess() throws Exception {
        Page<LocationDTO> page = new PageImpl<>(List.of(location));
        given(locationService.findByFilters(any(), eq(true), eq("Castillo"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/locations/filter")
                        .param("searchValue", "Castillo")
                        .param("isActive", "true")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fantasyName").value("Salón El Castillo"));
    }
}

