package com.semiclone.springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScreenDto {

    private Long id;    //  상영관 고유번호
    private Long cinemaId;    //  극장 고유번호
    private String name;    //  상영관 이름
    private Short totalSeat;    //  총 좌석수
    private String dimension;    //  상영 방식

}