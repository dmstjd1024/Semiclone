package com.semiclone.springboot.domain.seat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long>{

    Seat findOneById(Long screenId);

    Long countByScreenId(Long screenId);

    List<Seat> findByScreenId(Long screenId);

}//end of interface