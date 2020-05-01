package com.semiclone.springboot.web;

import java.util.Map;

import com.semiclone.springboot.domain.movie.MovieRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "ShowTime REST API")
@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieRestController {

    private final MovieRepository movieRepository;

    @ApiOperation(value = "영화 리스트 :: Movies(영화)")
    @GetMapping(value = "/")
    public Map<String, Object> d() throws Exception {
        return null;
    }

}//end of class