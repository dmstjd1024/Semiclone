package com.semiclone.springboot.domain.ticket;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@DynamicUpdate
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByTimeTableId(Long timeTableId);

    Ticket findOneById(Long ticketId);

    @Query("SELECT id FROM Ticket WHERE ticketToken = ?1")
    List<Integer> findIdByTicketToken(String ticketToken);
    
}//end of interface