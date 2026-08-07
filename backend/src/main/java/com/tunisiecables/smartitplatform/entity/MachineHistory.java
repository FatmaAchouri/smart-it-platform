package com.tunisiecables.smartitplatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "machine_history")
@Data
public class MachineHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long machineId;

    private LocalDateTime date;

    private String status;

    private Integer healthScore;

    private Integer failureRisk;

    private Integer daysUntilMaintenance;
}