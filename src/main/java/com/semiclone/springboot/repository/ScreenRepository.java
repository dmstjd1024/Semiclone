package com.semiclone.springboot.repository;

import java.util.List;

import com.semiclone.springboot.repository.entity.Screen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

    List<Screen> findByName(String name);
    
}//end of interface