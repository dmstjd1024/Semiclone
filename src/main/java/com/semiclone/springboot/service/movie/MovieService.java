package com.semiclone.springboot.service.movie;

import java.util.Map;

public interface MovieService {

    public Map<String, Object> getMoviesMap(String sort) throws Exception;

    public Map<String, Object> getMovieDetailMap(Long movieId) throws Exception;

}//end of interface