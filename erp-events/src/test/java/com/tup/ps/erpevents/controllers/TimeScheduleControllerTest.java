package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.task.schedule.TimeScheduleDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePostDTO;
import com.tup.ps.erpevents.dtos.task.schedule.TimeSchedulePutDTO;
import com.tup.ps.erpevents.services.TimeScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class TimeScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TimeScheduleService timeScheduleService;

    private TimeScheduleDTO timeSchedule;
    private TimeSchedulePostDTO timeSchedulePost;
    private TimeSchedulePutDTO timeSchedulePut;

    @BeforeEach
    void setUp() {
        timeSchedule = new TimeScheduleDTO();
        timeSchedule.setIdTimeSchedule(1L);
        timeSchedule.setTitle("Cronograma Día 1");
        timeSchedule.setIdEvent(100L);

        timeSchedulePost = new TimeSchedulePostDTO();
        timeSchedulePost.setTitle("Cronograma Día 1");
        timeSchedulePost.setIdEvent(100L);
        Map<LocalDateTime, Long> tasks = new HashMap<>();
        tasks.put(LocalDateTime.of(2025, 6, 1, 10, 0), 1L);
        timeSchedulePost.setScheduledTasks(tasks);

        timeSchedulePut = new TimeSchedulePutDTO();
        timeSchedulePut.setIdTimeSchedule(1L);
        timeSchedulePut.setTitle("Cronograma Actualizado");
    }

    @Test
    @DisplayName("TS-001/Should return all time schedules successfully")
    void testGetAllTimeSchedulesSuccess() throws Exception {
        Page<TimeScheduleDTO> page = new PageImpl<>(List.of(timeSchedule));
        given(timeScheduleService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/schedules")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Cronograma Día 1"));
    }

    @Test
    @DisplayName("TS-002/Should return time schedule by ID successfully")
    void testGetTimeScheduleByIdSuccess() throws Exception {
        given(timeScheduleService.findById(1L)).willReturn(Optional.of(timeSchedule));

        mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Cronograma Día 1"));
    }

    @Test
    @DisplayName("TS-003/Should return 404 when time schedule is not found by ID")
    void testGetTimeScheduleByIdNotFound() throws Exception {
        given(timeScheduleService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/schedules/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Cronograma no encontrado"));
    }

    @Test
    @DisplayName("TS-004/Should create time schedule successfully")
    void testCreateTimeScheduleSuccess() throws Exception {
        given(timeScheduleService.save(any(TimeSchedulePostDTO.class))).willReturn(timeSchedule);

        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timeSchedulePost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Cronograma Día 1"));
    }

    @Test
    @DisplayName("TS-005/Should return 400 when invalid time schedule post request")
    void testCreateTimeScheduleBadRequest() throws Exception {
        mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("TS-006/Should update time schedule successfully")
    void testUpdateTimeScheduleSuccess() throws Exception {
        given(timeScheduleService.update(eq(1L), any(TimeSchedulePutDTO.class))).willReturn(timeSchedule);

        mockMvc.perform(put("/schedules/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(timeSchedulePut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Cronograma Día 1"));
    }

    @Test
    @DisplayName("TS-007/Should delete time schedule successfully")
    void testDeleteTimeScheduleSuccess() throws Exception {
        mockMvc.perform(delete("/schedules/1"))
                .andExpect(status().isNoContent());
    }
}

