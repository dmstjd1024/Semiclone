package com.semiclone.springboot.service.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieMapping;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.web.dto.MovieDetailDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service(value = "movieServiceImpl")
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    public Map<String, Object> getMoviesMap(String sort) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        List<Movie> moviesList = new ArrayList<Movie>();

        if(sort.equals("") || sort == null || sort.equals("123890")){
            moviesList = movieRepository.findAllByOrderByReservationRateDesc(Movie.class);
        }else{
            moviesList = movieRepository.findAllByOrderByMovieTitleAsc(Movie.class);
        }

        List<Movie> returnList = new ArrayList<Movie>();
        if(moviesList.size() >= 10){
            for(int i=0; i<10; i++){
                returnList.add(moviesList.get(i));
            }
        }
        returnMap.put("movies", new Gson().fromJson(new Gson().toJson(returnList), returnList.getClass()));

        return returnMap;
    }//end of getMoviesMap

    public Map<String, Object> getMovieDetailMap(Long movieId) throws Exception {
       
        MovieDetailDto movie = new MovieDetailDto(movieRepository.findOneById(movieId));

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("movie", new Gson().fromJson(new Gson().toJson(movie), Movie.class));

        return returnMap;
    }//end of getMovieDetailMap

}//end of class