package com.semiclone.springboot.service.movie;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service(value = "movieServiceImpl")
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService{

    private MovieRepository movieRepository;

    public Map<String, Object> getMoviesMap(String sort) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if(sort.equals("") || sort == null){
            sort = "reservationRate";
        }



        return returnMap;
    }//end of getMoviesMap

    public Map<String, Object> getMovieDetailMap(Long movieId) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        
        Movie movie = movieRepository.findMovieById(movieId);
        returnMap.put("movie", new Gson().fromJson(new Gson().toJson(movie), Movie.class));

        return returnMap;
    }//end of getMovieDetailMap

}//end of class