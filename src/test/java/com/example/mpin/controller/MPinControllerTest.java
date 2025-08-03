package com.example.mpin.controller;

import com.example.mpin.model.MpinEntry;
import com.example.mpin.services.MpinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MpinController.class)
class MpinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MpinService service;

    private ObjectMapper objectMapper;
    private MpinEntry sampleEntry;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        sampleEntry = new MpinEntry();
        sampleEntry.setServiceId("abc");
        // set other fields as needed
    }

    @Test
    void loadAllEntries_shouldReturnList() throws Exception {
        List<MpinEntry> mockList = List.of(sampleEntry);
        when(service.getAllEntries()).thenReturn(mockList);

        mockMvc.perform(get("/api/mpin/loadAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void searchByServiceId_shouldReturnEntryWhenFound() throws Exception {
        when(service.getEntryByServiceId("abc")).thenReturn(Optional.of(sampleEntry));

        mockMvc.perform(get("/api/mpin/search/abc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceId").value("abc"));
    }


    @Test
    void searchByServiceId_shouldReturnNotFoundWhenMissing() throws Exception {
        when(service.getEntryByServiceId("xyz")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/mpin/search/xyz"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Service ID not found: xyz"));
    }


    @Test
    void deleteByServiceId_shouldReturnNoContentWhenDeleted() throws Exception {
        when(service.deleteByServiceId("abc")).thenReturn(true);

        mockMvc.perform(delete("/api/mpin/delete/abc"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteByServiceId_shouldReturnNotFoundWhenNotDeleted() throws Exception {
        when(service.deleteByServiceId("xyz")).thenReturn(false);

        mockMvc.perform(delete("/api/mpin/delete/xyz"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No record found with Service ID: xyz"));
    }
}

