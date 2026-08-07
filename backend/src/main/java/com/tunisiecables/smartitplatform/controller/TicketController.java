package com.tunisiecables.smartitplatform.controller;

import com.tunisiecables.smartitplatform.entity.Ticket;
import com.tunisiecables.smartitplatform.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/tickets")
    public String listTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "tickets";
    }

    @GetMapping("/tickets/new")
    public String newTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "ticket-form";
    }

    @PostMapping("/tickets")
    public String saveTicket(@ModelAttribute Ticket ticket) {
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setStatus("Open");
        ticket.setCreatedBy("user"); // Later we will get real username
        ticketRepository.save(ticket);
        return "redirect:/tickets";
    }

        // Admin - Change ticket status
    @GetMapping("/tickets/{id}/status")
    public String changeStatus(@PathVariable Long id, @RequestParam String status) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket != null) {
            ticket.setStatus(status);
            ticketRepository.save(ticket);
        }
        return "redirect:/tickets";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }



}