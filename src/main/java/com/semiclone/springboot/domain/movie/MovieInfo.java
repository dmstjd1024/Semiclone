package com.semiclone.springboot.domain.movie;

public interface MovieInfo {

    Long getId();    // 영화 고유번호
    String getMovieRating();    // 영화 관람등급
    String getMovieTitle();    // 영화 제목
    String getMovieTitleEng();    // 영화 영어제목
    String getMovieGenre();    // 영화 장르
    String getMovieTime();    // 영화 상영시간
    Long getReleaseDate();    // 개봉날짜
    String getReleaseType();    // 개봉종류

}//end of interface