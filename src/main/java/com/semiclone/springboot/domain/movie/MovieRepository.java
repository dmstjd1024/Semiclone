package com.semiclone.springboot.domain.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {
   
    Movie findOneByMovieTitle(String movieTitle);

    @Query("SELECT m FROM Movie m WHERE id = ?1")
    Movie findMovieById(Long id);

    MovieInfo findOneById(Long Id);

    <T> List<T> findAllByOrderByMovieTitleAsc(Class<T> type);

    <T> List<T> findAllByOrderByReservationRateDesc(Class<T> type);

    <T> List<T> findTop10ByOrderByMovieTitleAsc(Class<T> type);

    <T> List<T> findTop10ByOrderByReservationRateDesc(Class<T> type);
    
    <T> List<T> findAllBy(Class<T> type);

}//end of interface