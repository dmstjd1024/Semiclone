package com.semiclone.springboot.repository;

import com.semiclone.springboot.repository.entity.Test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {


}
