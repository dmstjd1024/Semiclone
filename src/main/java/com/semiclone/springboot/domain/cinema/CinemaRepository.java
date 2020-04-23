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

    @Query("SELECT id FROM Cinema WHERE cinemaArea = ?1")
    List<Long> findIdsByCinemaArea(String cinemaArea);

    @Query("SELECT cinemaArea FROM Cinema WHERE id = ?1")
    String findCinemaAreaById(Long cinemaId);

    @Query("SELECT id FROM Cinema WHERE cinemaArea = ?1")
    List<Long> findIdListByCinemaArea(String cinemaArea);

    @Query("SELECT cinemaName FROM Cinema WHERE id = ?1")
    String findCinemaNameById(Long cinemaId);

}//end of interface