package com.tunisiecables.smartitplatform.controller;

import com.tunisiecables.smartitplatform.entity.Machine;
import com.tunisiecables.smartitplatform.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AnalyticsController {

    @Autowired
    private MachineRepository machineRepository;

    @GetMapping("/analytics")
    public String analytics(Model model) {
        List<Machine> machines = machineRepository.findAll();

        // Basic Stats
        long total = machines.size();
        long active = machines.stream().filter(m -> "NORMAL".equalsIgnoreCase(m.getStatus()) || "En Service".equalsIgnoreCase(m.getStatus())).count();
        long hs = machines.stream().filter(m -> "HS".equalsIgnoreCase(m.getStatus())).count();

        // By Zone
        Map<String, Long> byZone = machines.stream()
                .filter(m -> m.getZone() != null)
                .collect(Collectors.groupingBy(Machine::getZone, Collectors.counting()));

        // By Production Line
        Map<String, Long> byLine = machines.stream()
                .filter(m -> m.getProductionLine() != null)
                .collect(Collectors.groupingBy(Machine::getProductionLine, Collectors.counting()));

        model.addAttribute("totalMachines", total);
        model.addAttribute("activeMachines", active);
        model.addAttribute("hsMachines", hs);
        model.addAttribute("byZone", byZone);
        model.addAttribute("byLine", byLine);

        return "analytics";
    }
}