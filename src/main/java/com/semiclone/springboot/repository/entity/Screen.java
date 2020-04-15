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
@Table(name = "screen")
@Getter
@ToString
@NoArgsConstructor
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  상영관 고유번호

    @Column(name = "cinema_id", nullable = false)
    private Long cinemaId;    //  극장 고유번호

    @Column(name = "name", length = 100, nullable = false)
    private String name;    //  상영관 이름

    @Column(name = "total_seat", nullable = false)
    private Short totalSeat;    //  총 좌석수

    @Column(name = "dimension", length = 20, nullable = false)
    private String dimension;    //  상영 방식

    //Constructor
    public Screen(Long cinemaId, String name, Short totalSeat, String dimension){
        this.cinemaId = cinemaId;
        this.name = name;
        this.totalSeat = totalSeat;
        this.dimension = dimension;
    }

}//end of class