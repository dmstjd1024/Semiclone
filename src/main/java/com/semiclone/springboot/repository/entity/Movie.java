package com.semiclone.springboot.repository.entity;

import java.sql.Clob;

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
@Table(name = "movie")
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;    // 영화 고유번호

    @Column(name = "movie_rating", length = 20, nullable = false)
    private String movieRating;    // 영화 관람등급

    @Column(name = "movie_title", length = 50, nullable = false)
    private String movieTitle;    // 영화 제목

    @Column(name = "movie_title_eng", length = 50, nullable = false)
    private String movieTitleEng;    // 영화 영어제목

    @Column(name = "movie_genre", length = 20, nullable = false)
    private String movieGenre;    // 영화 장르

    @Column(name = "movie_time", nullable = false)
    private String movieTime;    // 영화 상영시간

    @Column(name = "movie_image", nullable = false)
    private String movieImage;    // 영화 포스터

    @Column(name = "movie_drector", length = 20, nullable = false)
    private String movieDrector;    // 영화 감독

    @Column(name = "movie_actor", length = 200, nullable = false)
    private String movieActor;    // 영화 배우

    @Column(name = "movie_country", length = 10, nullable = false)
    private String movieCountry;    // 영화 제작국가

    @Column(name = "movie_intro", nullable = false)
    private Clob movieIntro;    // 영화 소개글

    @Column(name = "reservation_rate")
    private int reservationRate;    // 예매율

    @Column(name = "audience_count")
    private int audienceCount;    // 관람객 수
    
    @Column(name = "movie_grade_id")
    private int movieGradeId;    // 영화 한줄평

    @Column(name = "release_date", nullable = false)
    private Long releaseDate;    // 개봉날짜

    @Column(name = "release_type", length = 20, nullable = false)
    private String releaseType;    // 개봉종류

}//end of class