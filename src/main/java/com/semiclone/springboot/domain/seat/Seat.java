package com.semiclone.springboot.domain.seat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  좌석 고유번호

    @Column(nullable = false)
    private Long screenId;    //  상영관 고유번호

    @Column(length = 10, nullable = false)
    private String seatNo;    //  좌석 번호

    @Column(length = 50, nullable = false)
    private String seatName;    //  좌석 이름

    //Constructor
    public Seat(Long screenId,String seatNo, String seatName){
        this.screenId = screenId;
        this.seatNo = seatNo;
        this.seatName = seatName;
    }

}//end of class