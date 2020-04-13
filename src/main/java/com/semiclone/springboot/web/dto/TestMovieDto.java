package com.semiclone.springboot.web.dto;

import java.sql.Clob;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestMovieDto {

    private Long id;    //  영화 고유번호
    private String movieRating;    //  영화 관람등급
    private String movieTitle;    //  영화 제목
    private String movieTitleEng;    //  영화 영어제목
    private String movieGenre;    //  영화 장르
    private String movieTime;    //  영화 상영시간
    private String movieImage;    //  영화 포스터
    private String movieDrector;    //  영화감독
    private String movieActor;    //  영화배우
    private String movieCountry;    //  영화 제작국가  
    private Clob movieIntro;    //  영화 소개글 
    private int reservationRate;    //  예매율
    private int audienceCount;    //  관람객 수
    private int movieGradeId;    //  영화 한줄평
    private Long releaseDate;    //  개봉날짜
    private String releaseType;    //  개봉종류
    
}