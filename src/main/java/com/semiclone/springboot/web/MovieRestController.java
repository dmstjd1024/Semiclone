package com.semiclone.springboot.web;

import java.util.Map;

import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.timetable.TimeTable;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.service.movie.MovieService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "Movie REST API")
@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieService movieService;

    @ApiOperation(value = "영화 리스트 :: Movies(영화)")
    @GetMapping()
    public Map<String, Object> movies(@RequestParam("sort") String sort) throws Exception {
        return movieService.getMoviesMap(sort);
    }

    @ApiOperation(value = "영화 상세정보 :: Movie(영화)")
    @GetMapping(value = "/detail")
    public Map<String, Object> movies(@RequestParam("movieId") Long movieId) throws Exception {
        return movieService.getMovieDetailMap(movieId);
    }

}//end of class