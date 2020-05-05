package com.semiclone.springboot.domain.movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
   
    Movie findOneByMovieTitle(String movieTitle);

    Movie findOneById(Long Id);

    <T> List<T> findAllByOrderByMovieTitleAsc(Class<T> type);

    <T> List<T> findAllByOrderByReservationRateDesc(Class<T> type);
    
    <T> List<T> findAllBy(Class<T> type);

}//end of interface