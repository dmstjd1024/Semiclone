package com.semiclone.springboot.repository;

import com.semiclone.springboot.repository.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}