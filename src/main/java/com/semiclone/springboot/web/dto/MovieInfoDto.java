package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.movie.MovieInfo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MovieInfoDto {

    private Long id;    // 영화 고유번호
    private String movieRating;    // 영화 관람등급
    private String movieTitle;    // 영화 제목
    private String movieTitleEng;    // 영화 영어제목
    private String movieGenre;    // 영화 장르
    private String movieTime;    // 영화 상영시간
    private Long releaseDate;    // 개봉날짜
    private String releaseType;    // 개봉종류

    public MovieInfoDto(MovieInfo movieInfo){
        this.id = movieInfo.getId();
        this.movieRating = movieInfo.getMovieRating();
        this.movieTitle = movieInfo.getMovieTitle();
        this.movieTitleEng = movieInfo.getMovieTitleEng();
        this.movieGenre = movieInfo.getMovieGenre();
        this.movieTime = movieInfo.getMovieTime();
        this.releaseDate = movieInfo.getReleaseDate();
        this.releaseType = movieInfo.getReleaseType();
    }

}//end of class