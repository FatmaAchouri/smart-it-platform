package com.tunisiecables.smartitplatform.controller;

import com.tunisiecables.smartitplatform.entity.Notification;
import com.tunisiecables.smartitplatform.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String showNotifications(Model model) {
        List<Notification> notifications = notificationRepository.findAll();
        
        // Add sample notifications if empty
        if (notifications.isEmpty()) {
            Notification n1 = new Notification();
            n1.setTitle("Maintenance Alert");
            n1.setMessage("Machine Extruder-01 needs preventive maintenance");
            n1.setCreatedAt(LocalDateTime.now());
            n1.setRead(false);
            notificationRepository.save(n1);
            
            Notification n2 = new Notification();
            n2.setTitle("Ticket Resolved");
            n2.setMessage("Ticket #3 has been resolved by admin");
            n2.setCreatedAt(LocalDateTime.now().minusHours(2));
            n2.setRead(true);
            notificationRepository.save(n2);
        }
        
        model.addAttribute("notifications", notificationRepository.findAll());
        return "notifications";
    }
}