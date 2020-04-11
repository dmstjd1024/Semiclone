package com.semiclone.springboot.repository;

import com.semiclone.springboot.repository.entity.Cinema;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

}