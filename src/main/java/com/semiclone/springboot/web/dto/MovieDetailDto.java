package com.semiclone.springboot.web.dto;

import java.sql.Clob;

import com.semiclone.springboot.domain.movie.Movie;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MovieDetailDto {

    private Long Id;    // 영화 고유번호
    private String movieRating;    // 영화 관람등급
    private String movieTitle;    // 영화 제목
    private String movieTitleEng;    // 영화 영어제목
    private String movieGenre;    // 영화 장르
    private String movieTime;    // 영화 상영시간
    private String movieImage;    // 영화 이미지 경로
    private String movieDrector;    // 영화 감독
    private String movieActor;    // 영화 배우
    private String movieCountry;    // 영화 제작국가
    private Clob movieIntro;    // 영화 소개글
    private Float reservationRate;    // 예매율
    private int audienceCount;    // 관람객 수
    private int movieGradeId;    // 영화 한줄평
    private Long releaseDate;    // 개봉날짜
    private String releaseType;    // 개봉종류

    public MovieDetailDto(Movie movie){
        this.Id = movie.getId();
        this.movieRating = movie.getMovieRating();
        this.movieTitle = movie.getMovieTitle();
        this.movieTitleEng = movie.getMovieTitleEng();
        this.movieGenre = movie.getMovieGenre();
        this.movieTime = movie.getMovieTime();
        this.movieImage = movie.getMovieImage();
        this.movieDrector = movie.getMovieDrector();
        this.movieActor = movie.getMovieActor();
        this.movieCountry = movie.getMovieCountry();
        this.movieIntro = movie.getMovieIntro();
        this.reservationRate = movie.getReservationRate();
        this.audienceCount = movie.getAudienceCount();
        this.movieGradeId = movie.getMovieGradeId();
        this.releaseDate = movie.getReleaseDate();
        this.releaseType = movie.getReleaseType();
    }

}//end of class