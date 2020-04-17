package com.semiclone.springboot.domain.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
   
    List<Movie> findByMovieTitle(String movieTitle);
    
}//end of interface