package com.semiclone.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TestTimeTableDto {

    private Long id;
    private TestScreenDto testscreenDto;
    private TestMovieDto testMovieDto;
    private int turningNo;
    private String dimension;
    private int emptySeat;
    private Long date;
    private String startTime;
    private String endTime;
    private String state;
    
}