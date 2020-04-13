package com.semiclone.springboot.web.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestCinemaDto {

    private Long id;
    private List<TestMovieDto> testMovieDtos;
    private List<TestScreenDto> testScreenDtos;
    private String cinemaArea;
    private String cinemaName;
    
}