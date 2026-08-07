package com.tunisiecables.smartitplatform.controller;

import com.tunisiecables.smartitplatform.entity.Machine;
import com.tunisiecables.smartitplatform.entity.MachineHistory;
import com.tunisiecables.smartitplatform.repository.MachineRepository;
import com.tunisiecables.smartitplatform.repository.MachineHistoryRepository;
import com.tunisiecables.smartitplatform.service.MachineImportService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class MachineController {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private MachineHistoryRepository machineHistoryRepository;

    @Autowired
    private MachineImportService machineImportService;

    @GetMapping("/machines")
    public String listMachines(@RequestParam(required = false) String search, Model model) {
        List<Machine> machines;
        if (search != null && !search.isEmpty()) {
            machines = machineRepository.findAll().stream()
                .filter(m -> 
                    (m.getName() != null && m.getName().toLowerCase().contains(search.toLowerCase())) || 
                    (m.getType() != null && m.getType().toLowerCase().contains(search.toLowerCase())) ||
                    (m.getCodeEquipement() != null && m.getCodeEquipement().toLowerCase().contains(search.toLowerCase()))
                )
                .toList();
        } else {
            machines = machineRepository.findAll();
        }
        model.addAttribute("machines", machines);
        model.addAttribute("search", search);
        return "machines";
    }

    @GetMapping("/machines/new")
    public String newMachineForm(Model model) {
        model.addAttribute("machine", new Machine());
        return "machine-form";
    }

    @PostMapping("/machines")
    public String saveMachine(@ModelAttribute Machine machine) {
        machineRepository.save(machine);
        return "redirect:/machines";
    }

    @GetMapping("/machines/{id}/edit")
    public String editMachineForm(@PathVariable Long id, Model model) {
        Machine machine = machineRepository.findById(id).orElse(null);
        model.addAttribute("machine", machine);
        return "machine-form";
    }

    @GetMapping("/machines/{id}/delete")
    public String deleteMachine(@PathVariable Long id) {
        machineRepository.deleteById(id);
        return "redirect:/machines";
    }

    @GetMapping("/machines/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=machines.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Machines");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Code");
        header.createCell(2).setCellValue("Name");
        header.createCell(3).setCellValue("Type");
        header.createCell(4).setCellValue("Brand");
        header.createCell(5).setCellValue("Status");
        header.createCell(6).setCellValue("Production Line");
        header.createCell(7).setCellValue("Location");

        List<Machine> machines = machineRepository.findAll();
        int rowNum = 1;
        for (Machine m : machines) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(m.getId());
            row.createCell(1).setCellValue(m.getCodeEquipement());
            row.createCell(2).setCellValue(m.getName());
            row.createCell(3).setCellValue(m.getType());
            row.createCell(4).setCellValue(m.getBrand());
            row.createCell(5).setCellValue(m.getStatus());
            row.createCell(6).setCellValue(m.getProductionLine());
            row.createCell(7).setCellValue(m.getLocation());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/machines/import")
    public String showImportForm() {
        return "machine-import";
    }

    @PostMapping("/machines/import")
    public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (file.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Please select a file");
                return "redirect:/machines/import";
            }
            machineImportService.importFromExcel(file);
            redirectAttributes.addFlashAttribute("message", "✅ Import successful! " + file.getOriginalFilename());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "❌ Error: " + e.getMessage());
        }
        return "redirect:/machines";
    }

    @GetMapping("/suggestions")
    public String suggestions(Model model) {
        model.addAttribute("suggestions", List.of(
            "Check maintenance for Extruder-01 (high usage)",
            "PLC Control System needs firmware update",
            "Testing Machine Zwick shows warning"
        ));
        return "suggestions";
    }

    @GetMapping("/machines/{id}")
    public String machineDetail(@PathVariable Long id, Model model) {
        Optional<Machine> machineOpt = machineRepository.findById(id);
        if (machineOpt.isPresent()) {
            Machine machine = machineOpt.get();
            model.addAttribute("machine", machine);

            // Charger l'historique
            List<MachineHistory> history = machineHistoryRepository.findByMachineIdOrderByDateDesc(id);
            model.addAttribute("history", history);

            return "machine-detail";
        }
        return "redirect:/machines";
    }
}