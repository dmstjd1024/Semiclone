package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.cinema.Cinema;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CinemaDto {

    private Long id;    //  극장 고유번호
    private String cinemaArea;    //  극장 지역
    private String cinemaName;    //  극장 이름
    private boolean isAvailable;

    public CinemaDto(Cinema cinema){
        this.id = cinema.getId();
        this.cinemaArea = cinema.getCinemaArea();
        this.cinemaName = cinema.getCinemaName();
    }
    
    public void setIsVailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

}