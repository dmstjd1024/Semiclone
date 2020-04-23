package com.semiclone.springboot.service.timetable;

import java.util.Map;

public interface TimeTableService {

    public Map<String, Object> getCinemas() throws Exception;
    
    public Map<String, Object> getMovies() throws Exception;

    public Map<String, Object> getTimeTablesByCinemaId(Long cinemaId, Long date) throws Exception;

    public Map<String, Object> getTimeTablesByMovieId(Long movieId, String cinemaArea, Long date) throws Exception;

}