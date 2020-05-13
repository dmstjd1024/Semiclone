package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.movie.MovieMapping;

import lombok.Getter;

@Getter
public class MovieDto {

    private Long id;
    private String movieRating;    // 영화 관람등급
    private String movieTitle;    // 영화 제목
    private String movieImage;    // 영화 이미지 경로
    private boolean isAvailable;

    public MovieDto(MovieMapping movieMapping){
        this.id = movieMapping.getId();
        this.movieRating = movieMapping.getMovieRating();
        this.movieTitle = movieMapping.getMovieTitle();
        this.movieImage = movieMapping.getMovieImage();
    }
    
    public boolean getIsvailable(){
        return this.isAvailable;
    }

    public void setIsVailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

}//end of class