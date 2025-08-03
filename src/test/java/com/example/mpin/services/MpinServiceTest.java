package com.example.mpin.services;

import com.example.mpin.model.MpinEntry;
import com.example.mpin.repository.MpinRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MpinServiceTest {

    @Mock
    private MpinRepository repository;

    @InjectMocks
    private MpinService service;

    @Test
    void getAllEntries_shouldReturnList() {
        List<MpinEntry> mockList = List.of(new MpinEntry());
        when(repository.findAll()).thenReturn(mockList);

        List<MpinEntry> result = service.getAllEntries();

        assertEquals(1, result.size());
    }

    @Test
    void deleteByServiceId_shouldDeleteWhenExists() {
        MpinEntry entry = new MpinEntry();
        when(repository.findByServiceId("abc")).thenReturn(Optional.of(entry));

        boolean deleted = service.deleteByServiceId("abc");

        assertTrue(deleted);
        verify(repository).delete(entry);
    }

    @Test
    void deleteByServiceId_shouldReturnFalseWhenNotExists() {
        when(repository.findByServiceId("xyz")).thenReturn(Optional.empty());

        boolean deleted = service.deleteByServiceId("xyz");

        assertFalse(deleted);
        verify(repository, never()).delete(any());
    }
}

