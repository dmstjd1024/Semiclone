package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.seat.Seat;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SeatDto {

    private Long id;    //  좌석 고유번호
    private Long screenId;    //  상영관 고유번호
    private String seatNo;    //  좌석 번호
    private String seatName;    //  좌석 이름

    public SeatDto(Seat seat){
        this.id = seat.getId();
        this.screenId = seat.getScreenId();
        this.seatNo = seat.getSeatNo();
        this.seatName = seat.getSeatName();
    }
    
}//end of class