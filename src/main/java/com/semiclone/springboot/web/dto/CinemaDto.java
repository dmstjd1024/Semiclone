package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.cinema.Cinema;

import lombok.Getter;

@Getter
public class CinemaDto {

    private Long id;    //  극장 고유번호
    private String cinemaArea;    //  극장 지역
    private String cinemaName;  

    public CinemaDto(Cinema cinema){
        this.id = cinema.getId();
        this.cinemaArea = cinema.getCinemaArea();
        this.cinemaName = cinema.getCinemaName();
    }
    
}