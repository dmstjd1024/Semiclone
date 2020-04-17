package com.semiclone.springboot.domain.screen;

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
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  상영관 고유번호

    @Column(nullable = false)
    private Long cinemaId;    //  극장 고유번호

    @Column(length = 200, nullable = false)
    private String name;    //  상영관 이름

    @Column(nullable = false)
    private Short totalSeat;    //  총 좌석수

    @Column( length = 100, nullable = false)
    private String dimension;    //  상영 방식

    //Constructor
    public Screen(Long cinemaId, String name, Short totalSeat, String dimension){
        this.cinemaId = cinemaId;
        this.name = name;
        this.totalSeat = totalSeat;
        this.dimension = dimension;
    }

}//end of class