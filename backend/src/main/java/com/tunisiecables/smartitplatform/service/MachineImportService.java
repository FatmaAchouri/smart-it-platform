package com.tunisiecables.smartitplatform.service;

import com.tunisiecables.smartitplatform.entity.Machine;
import com.tunisiecables.smartitplatform.repository.MachineRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class MachineImportService {

    @Autowired
    private MachineRepository machineRepository;

    public void importFromExcel(MultipartFile file) throws Exception {
        List<Machine> machines = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() < 1) continue; // Skip header

                Machine m = new Machine();

                m.setCodeEquipement(getStringValue(row, 3));
                m.setName(getStringValue(row, 4));
                m.setNiveau(getStringValue(row, 5));
                m.setStatus(getStringValue(row, 6));
                m.setSerialNumber(getStringValue(row, 7));
                m.setLocation(getStringValue(row, 9));
                m.setZone(getStringValue(row, 10));
                m.setProductionLine(getStringValue(row, 11));
                m.setType(getStringValue(row, 12));

                // Dates
                m.setPurchaseDate(getDateValue(row, 22));
                m.setWarrantyStart(getDateValue(row, 20));
                m.setWarrantyEnd(getDateValue(row, 21));

                machines.add(m);
            }
        }

        machineRepository.saveAll(machines);
    }

    private String getStringValue(Row row, int col) {
        try {
            Cell cell = row.getCell(col);
            return cell != null ? cell.toString().trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private LocalDate getDateValue(Row row, int col) {
        try {
            Cell cell = row.getCell(col);
            if (cell == null) return null;
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}