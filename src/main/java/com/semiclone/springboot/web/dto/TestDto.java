package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.repository.entity.Test;

import lombok.Getter;

@Getter
public class TestDto {

    private Long id;
    private String context;

    public TestDto(Test test) {
        this.id = test.getId();
        this.context = test.getContext();
    }
}
