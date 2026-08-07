package com.tunisiecables.smartitplatform.repository;

import com.tunisiecables.smartitplatform.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}