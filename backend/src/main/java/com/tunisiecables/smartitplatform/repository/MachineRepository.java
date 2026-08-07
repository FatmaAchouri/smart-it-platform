package com.tunisiecables.smartitplatform.repository;

import com.tunisiecables.smartitplatform.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    long countByStatus(String status);   // For active machines
}