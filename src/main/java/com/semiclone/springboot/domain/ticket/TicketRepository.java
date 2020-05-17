package com.semiclone.springboot.domain.ticket;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@DynamicUpdate
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByTimeTableId(Long timeTableId);

    Ticket findOneById(Long ticketId);

    @Query("SELECT ticketState FROM Ticket WHERE id = ?1")
    char findTicketStateById(Long ticketId);
                                                                                    
    @Query("SELECT t.id AS ticketId, t.ticketState AS ticketState, "+ 
            "t.ticketPrice AS ticketPrice, s.seatNo AS seatNo, s.seatName AS seatName "+ 
            "FROM Ticket t LEFT OUTER JOIN Seat s ON t.seatId = s.id "+ 
            "WHERE t.timeTableId = ?1 AND SUBSTR(s.seatNo,1,1) = ?2")
    List<TicketMapping> findAllByTimeTableIdAndSeatRow(Long timeTableId, String seatRow);

    Integer countByTimeTableId(Long timeTableId);
    
}//end of interface