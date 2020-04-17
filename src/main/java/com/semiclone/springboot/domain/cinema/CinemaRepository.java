package com.semiclone.springboot.domain.cinema;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    List<Cinema> findByCinemaArea(String cinemaArea);

    List<Cinema> findByCinemaName(String cinemaName);

}//end of interface