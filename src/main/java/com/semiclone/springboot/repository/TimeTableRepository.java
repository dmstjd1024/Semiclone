package com.semiclone.springboot.repository;

import com.semiclone.springboot.repository.entity.TimeTable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long>{
    
}