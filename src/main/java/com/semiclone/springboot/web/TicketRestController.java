package com.semiclone.springboot.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.semiclone.springboot.web.dto.TestCinemaDto;
import com.semiclone.springboot.web.dto.TestMovieDto;
import com.semiclone.springboot.web.dto.TestScreenDto;
import com.semiclone.springboot.web.dto.TestTimeTableDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testticket")
public class TicketRestController {

    @GetMapping(value = "/select")
    public Map<String, Object> select() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();

        TestCinemaDto testCinemaDto = new TestCinemaDto();
        TestMovieDto testMovieDto = new TestMovieDto();
        TestScreenDto testScreenDto = new TestScreenDto();
        TestTimeTableDto testTimeTableDto = new TestTimeTableDto();

        testCinemaDto.setId((long)1);
        testCinemaDto.setCinemaArea("서울");
        testCinemaDto.setCinemaName("강남");
        
        testMovieDto.setId((long)1);
        testMovieDto.setMovieTitle("영화제목");
        testMovieDto.setMovieRating("청소년 관람불가");
        
        testTimeTableDto.setId((long)1);
        testTimeTableDto.setDate((long)20200413);

        List<TestMovieDto> testMovieDtoList = new ArrayList<TestMovieDto>();
        testMovieDtoList.add(testMovieDto);
        testCinemaDto.setTestMovieDtos(testMovieDtoList);

        List<TestScreenDto> testScreenDtoList = new ArrayList<TestScreenDto>();
        List<TestTimeTableDto> testTimeTableDtoList = new ArrayList<TestTimeTableDto>();

        testTimeTableDtoList.add(testTimeTableDto);
        testScreenDto.setTestTimeTableDtos(testTimeTableDtoList);
        testScreenDtoList.add(testScreenDto);

        testCinemaDto.setTestScreenDtos(testScreenDtoList);

        List<TestCinemaDto> selectList = new ArrayList<TestCinemaDto>();
        selectList.add(testCinemaDto);

        map.put("selectMap", selectList);
        return map;
    }

}