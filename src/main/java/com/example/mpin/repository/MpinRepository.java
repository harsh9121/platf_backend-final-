package com.example.mpin.repository;

import com.example.mpin.model.MpinEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MpinRepository extends JpaRepository <MpinEntry, Long>{
    Optional<MpinEntry> findByServiceId(String serviceId);
}
