package com.semiclone.springboot.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "seat")
@Getter
@ToString
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  좌석 고유번호

    @Column(name = "screen_id", nullable = false)
    private Long screenId;    //  상영관 고유번호

    @Column(name = "cinema_id", nullable = false)
    private Long cinemaId;    //  극장 고유번호

    @Column(name = "seat_no", nullable = false)
    private String seatNo;    //  좌석 번호

    @Column(name = "seat_name", nullable = false)
    private String seatName;    //  좌석 이름

    //Constructor
    public Seat(Long screenId, Long cinemaId, String seatNo, String seatName){
        this.screenId = screenId;
        this.cinemaId = cinemaId;
        this.seatNo = seatNo;
        this.seatName = seatName;
    }

}//end of class