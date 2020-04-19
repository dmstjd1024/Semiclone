package com.semiclone.springboot.service.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.ticket.TicketRepository;
import com.semiclone.springboot.web.dto.MovieDto;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service("ticketServiceImpl")
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    //Field
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final ScreenRepository screenRepository;
    private final TicketRepository ticketRepository;

    //Method
    /* 모든 영화, 극장, 날짜 리스트 return */
    public Map<String, String> getSelectMap() throws Exception{
        Map<String, String> map = new HashMap<String, String>();

        List<Movie> movieList = movieRepository.findAll();

        Gson gson = new Gson();
        String objJson = gson.toJson(movieList);

        map.put("movies", objJson);
       
        return map;
    }

    public Map<String, String> getSelectMap(Long movieId, String dimension, Long cinemaId, Long date, String group) throws Exception{
        return null;
    }
}//end of class

// Gson gson = new Gson();
// MovieDto vo = gson.fromJson(JsonObject.toString(), MovieDto.class);    //  json to Object