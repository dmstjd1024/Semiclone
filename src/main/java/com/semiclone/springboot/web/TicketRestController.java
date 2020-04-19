package com.semiclone.springboot.web;

import java.util.Map;

import com.semiclone.springboot.service.ticket.TicketService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("ticket")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketService ticketService;
               
    @ResponseBody
    @GetMapping(value = "/selects")
    public Map<String, String> select() throws Exception{
        return ticketService.getSelectMap();
    }

    @GetMapping(value = "/select")
    public Map<String, String> select(@RequestParam("movieId") Long movieId, @RequestParam("dimension") String dimension, 
                                        @RequestParam("cinemaId") Long cinemaId, @RequestParam("date") Long date, 
                                        @RequestParam String group) throws Exception{
        return ticketService.getSelectMap(movieId, dimension, cinemaId, date, group);
    }

}//end of class