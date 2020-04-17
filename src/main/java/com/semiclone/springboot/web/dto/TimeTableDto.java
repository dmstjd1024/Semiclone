package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.timetable.TimeTable;

import lombok.Getter;

@Getter
public class TimeTableDto {

    private Long id;    //  시간표 고유번호
    private Long screenId;    // 상영관 고유번호
    private Long movieId;    //  영화 고유번호
    private int turningNo;    //  회차
    private Long date;    //  상영 날짜
    private Long startTime;    //  상영 시작시간
    private Long endTime;    //  상영 종료시간
    private int emptySeat;    //  남은 좌석 수

    public TimeTableDto(TimeTable timeTable){
        this.id = timeTable.getId();
        this.screenId = timeTable.getScreenId();
        this.movieId = timeTable.getMovieId();
        this.turningNo = timeTable.getTurningNo();
        this.date = timeTable.getDate();
        this.startTime = timeTable.getStartTime();
        this.endTime = timeTable.getEndTime();
        this.emptySeat = timeTable.getEmptySeat();
    }

}