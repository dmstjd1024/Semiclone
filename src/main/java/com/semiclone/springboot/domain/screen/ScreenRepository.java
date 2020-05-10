package com.semiclone.springboot.domain.screen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    List<Screen> findByName(String name);

    Screen findOneById(Long screenId);
    
    @Query("SELECT cinemaId FROM Screen WHERE id = ?1")
    Long findCinemaIdById(Long id);

    @Query("SELECT id FROM Screen WHERE cinemaId = ?1 ORDER BY id ASC")
    List<Long> findIdByCinemaId(Long cinemaId);

}//end of interface