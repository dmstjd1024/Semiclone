package com.semiclone.springboot.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.semiclone.springboot.service.ticket.TicketService;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                                        @RequestParam("date") Long date, @RequestParam("group") String group) throws Exception {
        return ticketService.getScreensInfoMap(movieId, cinemaId, date, group);
    }

    @ApiOperation(value = "좌석 선택 :: Seats(좌석), Ticket(티켓)")
    @GetMapping(value = "/seats")
    public Map<String, Object> seats(@RequestParam("timeTableId") Long timeTableId) throws Exception {
        return ticketService.getSeatsMap(timeTableId);
    }

    @ApiOperation(value = "Ticketing",
            notes ="{\"state\":0, \"tickets\":[1234,5678]} -> 티켓팅 시작<br>"+
            "{\"state\":1, \"tickets\":[1234,5678]} -> 티켓팅 취소")
    @PatchMapping(value = "/ticketstate")
    public Map<String, Object> ticketState(@RequestBody Map<String, Object> tickets, HttpSession session) throws Exception {
        return ticketService.updateTicketState(tickets, session);
    }

    @ApiOperation(value = "기프트콘, 포인트 정보 가져오기 :: GiftCard(기프트콘), User(사용자)")
    @GetMapping(value = "/user/service")
    public Map<String, Object> service(@RequestParam("accountid") String accountId, HttpSession session) throws Exception {
        return ticketService.getUserService(accountId, session);
    }

    @ApiOperation(value = "티켓 결제 :: 추가완료 시 1 return / 실패 시 0 return",
            notes = "Server로 보낼 Data {\"imp_uid\":1234, \"movieCoupons\":[1234, 5678], \"tickets\":[1234, 5678], "+
            "\"giftCards\":[{\"giftCardId\" : 1234, \"useMoney\" : 500}, {\"giftCardId]\" : 5678, \"useMoney\" : 200}], "+
            "\"point\":[1000]}    // movieCoupons, tickes, giftCards가 없을 시 {\"imp_uid\":1234}")
    @PostMapping(value = "/payment")
    public Map<String, Object> payment(@RequestBody Map<String, Object> purchase, HttpSession session) throws Exception {
        return ticketService.addPurchase(purchase, session);
    }

}//end of class