package com.semiclone.springboot.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.web.dto.CinemaDto;
import com.semiclone.springboot.web.dto.MovieDto;
import com.semiclone.springboot.web.dto.ScreenDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("ticket")
@RequiredArgsConstructor
public class TicketRestController {

    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final TimeTableRepository timeTableRepository;
    private final CinemaRepository cinemaRepository;

    @GetMapping(value = "/select")
    public Map<String, Object> select() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        List<CinemaDto> list = new ArrayList<CinemaDto>();
        
        List<Cinema> cinemas = cinemaRepository.findAll();
        for(int i=0; i<cinemas.size(); i++){
            list.add(new CinemaDto(cinemas.get(i)));
        }


        map.put("cinemas", list);
        //map.put("selectMap",  new MovieDto( movieRepository.findById( (long)1 ).get() ).toString());
        //map.put("selectMap",  new MovieDto( movieRepository.findById( (long)1 ).get() ).toString());

        return map;
    }

}