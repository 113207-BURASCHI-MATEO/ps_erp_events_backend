package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.client.ClientDTO;
import com.tup.ps.erpevents.dtos.client.ClientPostDTO;
import com.tup.ps.erpevents.dtos.client.ClientPutDTO;
import com.tup.ps.erpevents.dtos.task.TaskDTO;
import com.tup.ps.erpevents.dtos.task.TaskPostDTO;
import com.tup.ps.erpevents.dtos.task.TaskPutDTO;
import com.tup.ps.erpevents.enums.DocumentType;
import com.tup.ps.erpevents.enums.TaskStatus;
import com.tup.ps.erpevents.services.ClientService;
import com.tup.ps.erpevents.services.TaskService;
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

import java.util.List;
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
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private TaskDTO task;
    private TaskPostDTO taskPost;
    private TaskPutDTO taskPut;

    @BeforeEach
    void setUp() {
        task = new TaskDTO();
        task.setIdTask(1L);
        task.setTitle("Instalar luces");
        task.setDescription("Instalación del sistema de iluminación del salón principal");
        task.setStatus(TaskStatus.PENDING);
        task.setIdEvent(2L);
        task.setSoftDelete(false);

        taskPost = new TaskPostDTO();
        taskPost.setTitle("Instalar luces");
        taskPost.setDescription("Instalación del sistema de iluminación del salón principal");
        taskPost.setStatus(TaskStatus.PENDING);
        taskPost.setIdEvent(2L);

        taskPut = new TaskPutDTO();
        taskPut.setIdTask(1L);
        taskPut.setTitle("Instalar luces");
        taskPut.setDescription("Instalación del sistema de iluminación del salón principal");
        taskPut.setStatus(TaskStatus.IN_PROGRESS);
        taskPut.setIdEvent(2L);
    }

    @Test
    @DisplayName("TC-001/Should return all tasks successfully")
    void testGetAllTasksSuccess() throws Exception {
        Page<TaskDTO> page = new PageImpl<>(List.of(task));
        given(taskService.findAll(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/tasks")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Instalar luces"));
    }

    @Test
    @DisplayName("TC-002/Should return task by ID successfully")
    void testGetTaskByIdSuccess() throws Exception {
        given(taskService.findById(1L)).willReturn(Optional.of(task));

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Instalar luces"));
    }

    @Test
    @DisplayName("TC-003/Should return 404 when task is not found by ID")
    void testGetTaskByIdNotFound() throws Exception {
        given(taskService.findById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Tarea no encontrada"));
    }

    @Test
    @DisplayName("TC-004/Should create task successfully")
    void testCreateTaskSuccess() throws Exception {
        given(taskService.save(any(TaskPostDTO.class))).willReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Instalar luces"));
    }

    @Test
    @DisplayName("TC-005/Should return 400 when invalid task post request")
    void testCreateTaskBadRequest() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("TC-006/Should update task successfully")
    void testUpdateTaskSuccess() throws Exception {
        given(taskService.update(eq(1L), any(TaskPutDTO.class))).willReturn(task);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskPut)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Instalar luces"));
    }

    @Test
    @DisplayName("TC-007/Should delete task successfully")
    void testDeleteTaskSuccess() throws Exception {
        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("TC-008/Should return tasks by filters successfully")
    void testFilterTasksSuccess() throws Exception {
        Page<TaskDTO> page = new PageImpl<>(List.of(task));
        given(taskService.findByFilters(any(), eq("PENDING"), eq(true), eq("luces"), any(), any()))
                .willReturn(page);

        mockMvc.perform(get("/tasks/filter")
                        .param("status", "PENDING")
                        .param("isActive", "true")
                        .param("searchValue", "luces")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Instalar luces"));
    }

    @Test
    @DisplayName("TC-009/Should update task status successfully")
    void testUpdateTaskStatusSuccess() throws Exception {
        task.setStatus(TaskStatus.COMPLETED);
        given(taskService.updateTaskStatus(eq(1L), eq(TaskStatus.COMPLETED))).willReturn(task);

        mockMvc.perform(patch("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TaskStatus.COMPLETED)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}
