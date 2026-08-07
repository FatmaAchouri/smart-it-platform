package com.tunisiecables.smartitplatform.repository;

import com.tunisiecables.smartitplatform.entity.MachineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MachineHistoryRepository extends JpaRepository<MachineHistory, Long> {
    List<MachineHistory> findByMachineIdOrderByDateDesc(Long machineId);
}