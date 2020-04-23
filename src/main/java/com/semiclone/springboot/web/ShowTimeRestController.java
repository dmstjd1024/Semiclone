package com.semiclone.springboot.web;

import java.util.Map;

import com.semiclone.springboot.service.timetable.TimeTableService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "ShowTime REST API")
@RestController
@RequestMapping("showtimes")
@RequiredArgsConstructor
public class ShowTimeRestController {

    private final TimeTableService timeTableService;

    @ApiOperation(value = "극장 리스트 :: Cinemas(극장), Dates(날짜) - DB의 첫번째 Cinema의 날짜 정보")
    @GetMapping(value = "/timetables/cinemas")
    public Map<String, Object> cinemas() throws Exception {
        return timeTableService.getCinemas();
    }

    @ApiOperation(value = "영화 리스트 :: Movies(영화), Areas(지역)")
    @GetMapping(value = "/timetables/movies")
    public Map<String, Object> movies() throws Exception {
        return timeTableService.getMovies();
    }

    @ApiOperation(value = "극장별 상영시간표 :: ")
    @GetMapping(value = "/timetables")
    public Map<String, Object> timetablesByCinemaId(@RequestParam("cinemaId") Long cinemaId, 
                                                    @RequestParam("date") Long date) throws Exception {
        return timeTableService.getTimeTablesByCinemaId(cinemaId, date);
    }

    @ApiOperation(value = "영화별 상영시간표 :: ")
    @GetMapping(value = "/timetables/data")
    public Map<String, Object> timetablesByMovies(@RequestParam("movieId") Long movieId, 
                                                    @RequestParam("cinemaArea") String cineamaArea, 
                                                    @RequestParam("date") Long date) throws Exception {
        return timeTableService.getTimeTablesByMovieId(movieId, cineamaArea, date);
    }

}//end of class