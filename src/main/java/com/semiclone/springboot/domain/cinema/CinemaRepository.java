package com.semiclone.springboot.domain.cinema;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    List<Cinema> findByCinemaArea(String cinemaArea);

    List<Cinema> findByCinemaName(String cinemaName);

    @Query("SELECT cinemaArea FROM Cinema GROUP BY cinemaArea ORDER BY id")
    List<String> findCinemaArea();

    List<Cinema> findAllByCinemaArea(String cinemaArea);

    List<Cinema> findAllByCinemaAreaAndId(String cinemaArea, Long id);

    Long findIdByCinemaArea(String cinemaArea);

}//end of interface