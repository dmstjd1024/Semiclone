package com.semiclone.springboot.domain.movie;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Clob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 영화 고유번호

    @Column(length = 20, nullable = false)
    private String movieRating;    // 영화 관람등급

    @Column(length = 50, nullable = false)
    private String movieTitle;    // 영화 제목

    @Column(length = 50, nullable = false)
    private String movieTitleEng;    // 영화 영어제목

    @Column(length = 200, nullable = false)
    private String movieGenre;    // 영화 장르

    @Column(nullable = false)
    private String movieTime;    // 영화 상영시간

    @Column(nullable = false)
    private String movieImage;    // 영화 이미지 경로

    @Column(length = 20, nullable = false)
    private String movieDrector;    // 영화 감독

    @Column(length = 200, nullable = false)
    private String movieActor;    // 영화 배우

    @Column(length = 10, nullable = false)
    private String movieCountry;    // 영화 제작국가

    @Column(nullable = false)
    private Clob movieIntro;    // 영화 소개글

    private Float reservationRate;    // 예매율

    private int audienceCount;    // 관람객 수
    
    private int movieGradeId;    // 영화 한줄평

    @Column(nullable = false)
    private Long releaseDate;    // 개봉날짜

    @Column(length = 20, nullable = false)
    private String releaseType;    // 개봉종류

    @Builder                                                        
    public Movie(String movieRating, String movieTitle, String movieTitleEng, 
                String movieGenre, String movieTime, String movieImage, String movieDrector, 
                String movieActor, String movieCountry, Clob movieIntro, Float reservationRate, 
                int audienceCount, int movieGradeId, Long releaseDate, String releaseType){
        this.movieRating = movieRating;
        this.movieTitle = movieTitle;
        this.movieTitleEng = movieTitleEng;
        this.movieGenre = movieGenre;
        this.movieTime = movieTime;
        this.movieImage = movieImage;
        this.movieDrector = movieDrector;
        this.movieActor = movieActor;
        this.movieCountry = movieCountry;
        this.movieIntro = movieIntro;
        this.reservationRate = reservationRate;
        this.audienceCount = audienceCount;
        this.movieGradeId = movieGradeId;
        this.releaseDate = releaseDate;
        this.releaseType = releaseType;
    }

}//end of class