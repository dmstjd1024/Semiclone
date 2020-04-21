package com.semiclone.springboot.web;

import java.util.Map;

import com.semiclone.springboot.service.ticket.TicketService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("ticket")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketService ticketService;
               
    @GetMapping(value = "/select")
    public Map<String, Object> select() throws Exception{
        return ticketService.getSelectMap();
    }

    @GetMapping(value = "/select/")
    public Map<String, Object> select(@RequestParam("movieId") Long movieId, @RequestParam("cinemaId") Long cinemaId, 
                                        @RequestParam("date") Long date, @RequestParam("timeTableId") Long timeTableId, 
                                        @RequestParam("group") String group) throws Exception{
        return ticketService.getSelectMap(movieId, cinemaId, date, timeTableId, group);
    }

}//end of class