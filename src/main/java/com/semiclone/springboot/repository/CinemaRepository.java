package com.semiclone.springboot.repository;

import java.util.List;

import com.semiclone.springboot.repository.entity.Cinema;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    List<Cinema> findByCinemaArea(String cinemaArea);

    List<Cinema> findByCinemaName(String cinemaName);

}//end of interface