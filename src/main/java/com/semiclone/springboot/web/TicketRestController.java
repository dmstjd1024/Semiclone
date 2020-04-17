package com.semiclone.springboot.web;

import java.util.HashMap;
import java.util.Map;

import com.semiclone.springboot.domain.movie.MovieRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("testticket")
@RequiredArgsConstructor
public class TicketRestController {

    private final MovieRepository movieRepository;

    @GetMapping(value = "/select")
    public Map<String, Object> select() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("selectMap", "test");
        return map;
    }

}