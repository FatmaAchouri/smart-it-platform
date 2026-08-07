package com.tunisiecables.smartitplatform.controller;

import com.tunisiecables.smartitplatform.entity.Machine;
import com.tunisiecables.smartitplatform.repository.MachineRepository;
import com.tunisiecables.smartitplatform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Machine> allMachines = machineRepository.findAll();
        long totalMachines = allMachines.size();

        long activeMachines = allMachines.stream()
                .filter(m -> m.getStatus() != null &&
                        (m.getStatus().equalsIgnoreCase("NORMAL") ||
                         m.getStatus().equalsIgnoreCase("En Service") ||
                         m.getStatus().equalsIgnoreCase("Active")))
                .count();

        long hsMachines = allMachines.stream()
                .filter(m -> m.getStatus() != null &&
                        (m.getStatus().equalsIgnoreCase("HS") ||
                         m.getStatus().equalsIgnoreCase("En Panne") ||
                         m.getStatus().equalsIgnoreCase("Maintenance") ||
                         m.getStatus().equalsIgnoreCase("Broken")))
                .count();

        // Health Score
        double healthScore = totalMachines > 0 ? (double) activeMachines / totalMachines * 100 : 0;
        model.addAttribute("healthScore", Math.round(healthScore));

        // Machines by Zone
        Map<String, Long> machinesByZone = allMachines.stream()
                .filter(m -> m.getZone() != null)
                .collect(Collectors.groupingBy(Machine::getZone, Collectors.counting()));

        long openTickets = ticketRepository.count();

        model.addAttribute("totalMachines", totalMachines);
        model.addAttribute("activeMachines", activeMachines);
        model.addAttribute("hsMachines", hsMachines);
        model.addAttribute("openTickets", openTickets);
        model.addAttribute("machinesByZone", machinesByZone);

        return "dashboard";
    }

    @GetMapping("/predictive")
    public String predictiveMaintenance(Model model) {
        List<Machine> machines = machineRepository.findAll();

        for (Machine m : machines) {
            int usageFactor = m.getStatus() != null && m.getStatus().contains("HS") ? 30 : 70;
            int ageFactor = 85;

            int healthScore = (int) (usageFactor * 0.6 + ageFactor * 0.4);
            int failureRisk = 100 - healthScore;

            m.setHealthScore(Math.max(10, Math.min(100, healthScore)));
            m.setFailureRisk(failureRisk);

            if (healthScore > 75) m.setHealthStatus("HEALTHY");
            else if (healthScore > 50) m.setHealthStatus("WARNING");
            else m.setHealthStatus("CRITICAL");

            m.setDaysUntilMaintenance((int) (Math.random() * 30) + 5);
        }

        model.addAttribute("machines", machines);
        return "predictive";
    }

    @GetMapping("/executive")
        public String executiveDashboard(Model model) {
        // Reuse existing stats
        List<Machine> allMachines = machineRepository.findAll();
        long totalMachines = allMachines.size();
        long activeMachines = allMachines.stream()
                .filter(m -> m.getStatus() != null && 
                        (m.getStatus().equalsIgnoreCase("NORMAL") || 
                        m.getStatus().equalsIgnoreCase("En Service")))
                .count();
        long hsMachines = allMachines.stream()
                .filter(m -> m.getStatus() != null && m.getStatus().equalsIgnoreCase("HS"))
                .count();

        double healthScore = totalMachines > 0 ? (double) activeMachines / totalMachines * 100 : 0;

        model.addAttribute("totalMachines", totalMachines);
        model.addAttribute("activeMachines", activeMachines);
        model.addAttribute("hsMachines", hsMachines);
        model.addAttribute("healthScore", Math.round(healthScore));

        return "executive";
        }







}