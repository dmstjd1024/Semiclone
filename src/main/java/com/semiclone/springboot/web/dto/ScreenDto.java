package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.screen.Screen;

import lombok.Getter;

@Getter
public class ScreenDto {

    private Long id;    //  상영관 고유번호
    private Long cinemaId;    //  극장 고유번호
    private String name;    //  상영관 이름
    private Short totalSeat;    //  총 좌석수
    private String dimension;    //  상영 방식

    public ScreenDto(Screen screen){
        this.id = screen.getId();
        this.cinemaId = screen.getCinemaId();
        this.name = screen.getName();
        this.totalSeat = screen.getTotalSeat();
        this.dimension = screen.getDimension();
    }

}