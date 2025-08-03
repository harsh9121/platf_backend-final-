package com.example.mpin.services;

import com.example.mpin.model.MpinEntry;
import com.example.mpin.repository.MpinRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MpinService {

    @Autowired
    private final MpinRepository repository;

    public List<MpinEntry> getAllEntries() {
        return repository.findAll();
    }

    public Optional<MpinEntry> getEntryByServiceId(String serviceId) {
        return repository.findByServiceId(serviceId);
    }




    public boolean deleteByServiceId(String serviceId) {
        Optional<MpinEntry> entry = repository.findByServiceId(serviceId);
        if (entry.isPresent()) {
            repository.delete(entry.get());
            return true;
        } else {
            return false;
        }
    }
}
