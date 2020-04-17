package com.semiclone.springboot.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testticket")
public class TicketRestController {

    @GetMapping(value = "/select")
    public Map<String, Object> select() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("selectMap", "Test");
        return map;
    }

}