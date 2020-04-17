package com.semiclone.springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CinemaDto {

    private Long id;    //  극장 고유번호
    private String cinemaArea;    //  극장 지역
    private String cinemaName;  

}