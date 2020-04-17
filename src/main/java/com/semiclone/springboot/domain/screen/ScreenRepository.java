package com.semiclone.springboot.domain.screen;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    List<Screen> findByName(String name);
    
}//end of interface