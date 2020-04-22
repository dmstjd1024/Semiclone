package com.semiclone.springboot.web;

import java.util.Map;

import com.semiclone.springboot.service.ticket.TicketService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
@Api(tags = "Ticket REST API")
@RestController
@RequestMapping("ticket")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketService ticketService;
    
    @ApiOperation(value = "첫페이지 렌더링 :: 모든 Movies(영화), Cinemas(극장), Dates(날짜)")
    @GetMapping(value = "/screens")
    public Map<String, Object> screens() throws Exception {
        return ticketService.getScreensMap();
    }

    @ApiOperation(value = "빠른 예매 :: Movies(영화), Cinemas(극장), Dates(날짜), TimeTables(시간)",
            notes = "Parameter Value (Null = 123890) / 모든 Value가 null일 경우 : 모든 정보 가져오기")
    @GetMapping(value = "/screens/info")
    public Map<String, Object> screens(@RequestParam("movieId") Long movieId, @RequestParam("cinemaId") Long cinemaId, 
                                        @RequestParam("date") Long date, @RequestParam("timeTableId") Long timeTableId, 
                                        @RequestParam("group") String group) throws Exception {
        return ticketService.getScreensInfoMap(movieId, cinemaId, date, timeTableId, group);
    }

    @ApiOperation(value = "좌석 선택 :: Seats(좌석), Ticket(티켓)")
    @GetMapping(value = "/seats")
    public Map<String, Object> seats(@RequestParam("timeTableId") Long timeTableId) throws Exception {
        return null;
    }

    

}//end of class