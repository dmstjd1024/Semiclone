package com.semiclone.springboot.domain.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {
   
    @Query("SELECT m FROM Movie m WHERE movieTitle = ?1")
    List<Movie> findByMovieTitle(String movieTitle);

    @Query("SELECT m FROM Movie m WHERE id = ?1")
    Movie findMovieById(Long id);

    @Query("SELECT m FROM Movie m WHERE id = ?1")
    Movie findOneById(Long movieId);

    @Query("SELECT m FROM Movie m ORDER BY movieTitle ASC")
    List<Movie> findAllOrderByMovieTitle();

    @Query("SELECT m FROM Movie m ORDER BY reservationRate DESC")
    List<Movie> findAllOrderByReservationRate();

}//end of interface