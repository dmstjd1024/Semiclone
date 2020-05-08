package com.semiclone.springboot.domain.movie;

public interface MovieMapping {

    Long getId();    //  영화 고유번호
    String getMovieRating();    //  영화 관람등급
    String getMovieTitle();    //  영화 제목
    String getMovieImage();    //  영화 이미지 경로

}