package com.semiclone.springboot.domain.timetable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  시간표 고유번호

    @Column(nullable = false)
    private Long screenId;    // 상영관 고유번호

    @Column(nullable = false)
    private Long movieId;    //  영화 고유번호

    @Column(nullable = false)
    private int turningNo;    //  회차

    @Column(nullable = false)
    private Long date;    //  상영 날짜

    @Column(length = 10, nullable = false)
    private Long startTime;    //  상영 시작시간

    @Column(length = 10, nullable = false)
    private Long endTime;    //  상영 종료시간

    @Column(nullable = false)
    private Long cinemaId;    //  극장 고유번호

    @Builder
    public TimeTable(Long screenId, Long movieId, int turningNo, Long date, Long startTime, Long endTime, Long cinemaId){
        this.screenId = screenId;
        this.movieId = movieId;
        this.turningNo = turningNo;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaId = cinemaId;
    }

}//end of class