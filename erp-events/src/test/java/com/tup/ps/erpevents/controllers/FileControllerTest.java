package com.tup.ps.erpevents.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tup.ps.erpevents.dtos.file.FileDTO;
import com.tup.ps.erpevents.dtos.file.FilePostDTO;
import com.tup.ps.erpevents.dtos.file.FilePutDTO;
import com.tup.ps.erpevents.services.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@mail.com", roles = {"ADMIN"})
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileService fileService;

    private FileDTO file;
    private FilePostDTO filePost;
    private FilePutDTO filePut;

    @BeforeEach
    void setUp() {
        file = new FileDTO();
        file.setIdFile(1L);
        file.setFileName("example.pdf");
        file.setFileType("application/pdf");
        file.setSoftDelete(false);

        filePost = new FilePostDTO();
        filePost.setFileName("example.pdf");
        filePost.setFileType("application/pdf");

        filePut = new FilePutDTO();
        filePut.setFileName("updated.pdf");
        filePut.setFileType("application/pdf");
    }

    @Test
    @DisplayName("FC-001/Should return all files successfully")
    void testGetAllFilesSuccess() throws Exception {
        Page<FileDTO> page = new PageImpl<>(List.of(file));
        given(fileService.findAll(any())).willReturn(page);

        mockMvc.perform(get("/files"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fileName").value("example.pdf"));
    }

    @Test
    @DisplayName("FC-002/Should return file by ID successfully")
    void testGetFileByIdSuccess() throws Exception {
        given(fileService.getById(1L)).willReturn(Optional.of(file));

        mockMvc.perform(get("/files/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("example.pdf"));
    }

    @Test
    @DisplayName("FC-003/Should return 404 when file is not found by ID")
    void testGetFileByIdNotFound() throws Exception {
        given(fileService.getById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/files/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Archivo no encontrado"));
    }

    @Test
    @DisplayName("FC-004/Should delete file successfully")
    void testDeleteFileSuccess() throws Exception {
        mockMvc.perform(delete("/files/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("FC-005/Should return files by filters successfully")
    void testFilterFilesSuccess() throws Exception {
        Page<FileDTO> page = new PageImpl<>(List.of(file));
        given(fileService.findByFilters(any(), any(), any(), any(), any(), any())).willReturn(page);

        mockMvc.perform(get("/files/filter")
                        .param("fileType", "application/pdf")
                        .param("softDelete", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].fileName").value("example.pdf"));
    }

    @Test
    @DisplayName("FC-006/Should save file successfully")
    void testSaveFileSuccess() throws Exception {
        MockMultipartFile mockData = new MockMultipartFile("data", "", "application/json",
                objectMapper.writeValueAsBytes(filePost));
        MockMultipartFile mockFile = new MockMultipartFile("file", "example.pdf", "application/pdf", "PDF content".getBytes());

        given(fileService.save(any(FilePostDTO.class), any(MultipartFile.class))).willReturn(file);

        mockMvc.perform(multipart("/files")
                        .file(mockData)
                        .file(mockFile))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileName").value("example.pdf"));
    }

    @Test
    @DisplayName("FC-007/Should update file successfully")
    void testUpdateFileSuccess() throws Exception {
        MockMultipartFile mockData = new MockMultipartFile("data", "", "application/json",
                objectMapper.writeValueAsBytes(filePut));
        MockMultipartFile mockFile = new MockMultipartFile("file", "updated.pdf", "application/pdf", "Updated PDF content".getBytes());

        given(fileService.update(eq(1L), any(FilePutDTO.class), any(MultipartFile.class))).willReturn(file);

        mockMvc.perform(multipart("/files/1")
                        .file(mockData)
                        .file(mockFile)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("example.pdf"));
    }
}
