package com.personal.mapis.repositories;

import com.personal.mapis.models.entities.Permit;
import com.personal.mapis.models.enums.PermitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermitRepository extends JpaRepository<Permit, Long> {
    Optional<Permit> findByPermitNumber(String permitNumber);

    long countByStatus(PermitStatus status);
}
