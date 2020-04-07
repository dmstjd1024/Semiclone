package com.semiclone.springboot.web;

import java.util.HashMap;
import java.util.Map;

import com.semiclone.springboot.service.TestService;
import com.semiclone.springboot.web.dto.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test() {

        TestDto testDto = testService.findById((long)1);

        System.out.println(testDto.getContext());

        return new ModelAndView("test").addObject(testDto);
    }

    @GetMapping(value = "/start")
    public Map<String, Object> start() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "동작테스트 JSON으로 return 성공");
        return map;
    }

}
