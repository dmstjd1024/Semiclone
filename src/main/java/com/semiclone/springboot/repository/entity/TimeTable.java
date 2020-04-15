package com.semiclone.springboot.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "timetable")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  시간표 고유번호

    @Column(name = "screen_id", nullable = false)
    private Long screenId;    // 상영관 고유번호

    @Column(name = "movie_id", nullable = false)
    private Long movieId;    //  영화 고유번호

    @Column(name = "turning_no", nullable = false)
    private int turningNo;    //  회차

    @Column(name = "date", nullable = false)
    private Long date;    //  상영 날짜

    @Column(name = "start_time", length = 10, nullable = false)
    private Long startTime;    //  상영 시작시간

    @Column(name = "end_time", length = 10, nullable = false)
    private Long endTime;    //  상영 종료시간

    @Column(name = "empty_seat", nullable = false)
    private int emptySeat;    //  남은 좌석 수

}//end of class