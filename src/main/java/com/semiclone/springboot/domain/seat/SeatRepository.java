package com.semiclone.springboot.domain.seat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long>{

    List<Seat> findByScreenId(Long screenId);

    Long countByScreenId(Long screenId);

}//end of interface