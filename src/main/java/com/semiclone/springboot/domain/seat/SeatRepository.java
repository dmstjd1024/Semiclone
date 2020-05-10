package com.semiclone.springboot.domain.seat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long>{

    Seat findOneById(Long screenId);

    Long countByScreenId(Long screenId);

    List<Seat> findByScreenId(Long screenId);

    @Query("SELECT SUBSTRING(seatNo,1,1) FROM Seat WHERE screenId = ?1 GROUP BY SUBSTRING(seatNo,1,1)")
    List<String> findSeatRowsByScreenId(Long screenId);

}//end of interface