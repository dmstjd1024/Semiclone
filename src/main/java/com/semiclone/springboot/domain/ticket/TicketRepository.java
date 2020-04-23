package com.semiclone.springboot.domain.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByTimeTableId(Long timeTableId);
    
}//end of interface