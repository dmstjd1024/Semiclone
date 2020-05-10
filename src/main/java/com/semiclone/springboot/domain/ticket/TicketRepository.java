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

    @Query("SELECT screenId FROM Ticket WHERE timeTableId = ?1 GROUP BY screenId")
    Long findScreenIdByTimeTableId(Long timeTableId);

    @Query("SELECT t.id AS id, t.ticketState AS ticketState, t.ticketPrice AS ticketPrice, t.seatId AS seatId FROM Ticket t LEFT OUTER JOIN Seat s ON t.seatId = s.id WHERE t.timeTableId = ?1 AND SUBSTR(s.seatNo,1,1) = ?2")
    List<TicketMapping> findAllByTimeTableIdAndSeatRow(Long timeTableId, String row);
    
}//end of interface