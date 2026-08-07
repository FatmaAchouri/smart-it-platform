package com.tunisiecables.smartitplatform.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "machine")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeEquipement;        // Code équipement
    private String name;                  // Description de l'équipement
    private String type;                  // Famille / Type
    private String brand;                 // Constructeur
    private String model;                 // N° de modèle
    private String serialNumber;          // N° de série
    private String productionLine;        // Ligne de production
    private String location;              // Emplacement
    private String zone;                  // Zone
    private String status;                // État (NORMAL, HS, etc.)
    private String niveau;                // Niveau

    private LocalDate purchaseDate;       // Date de mise en service
    private LocalDate warrantyStart;      // Date de début de garantie
    private LocalDate warrantyEnd;        // Date de fin de garantie

    private Integer healthScore;
    private Integer failureRisk;
    private Integer daysUntilMaintenance;
    private String healthStatus;

    // Getters and Setters
    public Integer getHealthScore() { return healthScore; }
    public void setHealthScore(Integer healthScore) { this.healthScore = healthScore; }

    public Integer getFailureRisk() { return failureRisk; }
    public void setFailureRisk(Integer failureRisk) { this.failureRisk = failureRisk; }

    public Integer getDaysUntilMaintenance() { return daysUntilMaintenance; }
    public void setDaysUntilMaintenance(Integer daysUntilMaintenance) { this.daysUntilMaintenance = daysUntilMaintenance; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }
    










    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodeEquipement() { return codeEquipement; }
    public void setCodeEquipement(String codeEquipement) { this.codeEquipement = codeEquipement; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getProductionLine() { return productionLine; }
    public void setProductionLine(String productionLine) { this.productionLine = productionLine; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public LocalDate getWarrantyStart() { return warrantyStart; }
    public void setWarrantyStart(LocalDate warrantyStart) { this.warrantyStart = warrantyStart; }

    public LocalDate getWarrantyEnd() { return warrantyEnd; }
    public void setWarrantyEnd(LocalDate warrantyEnd) { this.warrantyEnd = warrantyEnd; }
}