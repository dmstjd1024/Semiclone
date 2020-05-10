package com.semiclone.springboot.service.ticket;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.semiclone.springboot.config.IamPortConfig;
import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.coupon.MovieCoupon;
import com.semiclone.springboot.domain.coupon.MovieCouponRepository;
import com.semiclone.springboot.domain.giftcard.GiftCard;
import com.semiclone.springboot.domain.giftcard.GiftCardRepository;
import com.semiclone.springboot.domain.movie.MovieMapping;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.payment.Payment;
import com.semiclone.springboot.domain.payment.PaymentRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.seat.Seat;
import com.semiclone.springboot.domain.seat.SeatRepository;
import com.semiclone.springboot.domain.ticket.Ticket;
import com.semiclone.springboot.domain.ticket.TicketMapping;
import com.semiclone.springboot.domain.ticket.TicketRepository;
import com.semiclone.springboot.domain.tickethistory.TicketHisotryRepository;
import com.semiclone.springboot.domain.tickethistory.TicketHistory;
import com.semiclone.springboot.domain.timetable.TimeTable;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.web.dto.CinemaDto;
import com.semiclone.springboot.web.dto.MovieDto;
import com.semiclone.springboot.web.dto.TicketBySeatDto;
import com.semiclone.springboot.web.dto.TimeTableDto;
import com.semiclone.springboot.web.dto.iamport.AccessToken;
import com.semiclone.springboot.web.dto.iamport.AuthData;
import com.semiclone.springboot.web.dto.iamport.IamportResponse;
import com.semiclone.springboot.web.dto.iamport.Purchase;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service("ticketServiceImpl")
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService{

    //Field
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final TimeTableRepository timeTableRepository;
    private final ScreenRepository screenRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final AccountRepository accountRepository;
    private final GiftCardRepository giftCardRepository;
    private final MovieCouponRepository movieCouponRepository;
    private final PaymentRepository paymentRepository;
    private final TicketHisotryRepository ticketHisotryRepository;
    private final IamPortConfig iamPortConfig;

    //Method
    /* 모든 영화, 극장, 날짜 */
    public Map<String, Object> getScreensMap() throws Exception{        

        List<MovieDto> moviesList = this.getMoviesList(null, "0");    //  Movies
        List<Map<String, Object>> cinemasList = this.getCinemasList(null);    //  Cinemas
        List<Map<String, Object>> datesList = this.getDatesList(null);    //  Dates
        
        return this.getReturnJsonMap(moviesList, cinemasList, datesList, null);

    }//end of getScreensMap

    /* 선택한 정보를 포함한 상영정보 */
    public Map<String, Object> getScreensInfoMap(Long movieId, Long cinemaId, Long date, String group) throws Exception{
        
        /* Test용 로직 :: Swagger UI에서 Param값에 null 지원을 안하므로, 로직으로 null 처리*/
        movieId = (movieId == null || movieId == 123890) ? null : movieId;
        cinemaId = (cinemaId == null || cinemaId == 123890) ? null : cinemaId;
        date = (date == null || date == 123890) ? null : date;
        group = (group == null || group.equals("") || group.equals("123890")) ? "0" : "1";

        List<MovieDto> moviesList = null;
        List<Map<String, Object>> cinemasList = null;
        List<Map<String, Object>> datesList = null;
        List<Map<String, Object>> screensList = null;

        if(movieId != null && cinemaId == null && date == null){    // 선택한 영화의 상영극장&상영날짜
            cinemasList = this.getCinemasList(timeTableRepository.findCinemaIdByMovieId(movieId));
            datesList = this.getDatesList(timeTableRepository.findDateByMovieId(movieId));
        }

        if(cinemaId != null && movieId == null && date == null){    // 선택한 극장의 상영영화&상영날짜
            moviesList = this.getMoviesList(timeTableRepository.findMovieIdByCinemaId(cinemaId), group);
            datesList = this.getDatesList(timeTableRepository.findDateByCinemaId(cinemaId));
        }

        if(date != null && cinemaId == null && movieId == null){    // 선택한 날짜의 상영영화&상영극장
            moviesList = this.getMoviesList(timeTableRepository.findMovieIdByDate(date), group);
            cinemasList = this.getCinemasList(timeTableRepository.findCinemaIdIdByDate(date));
        }

        if(cinemaId != null && movieId != null && date == null){    // 선택한 극장&영화의 상영날짜
            moviesList = this.getMoviesList(timeTableRepository.findMovieIdByCinemaId(cinemaId), group);
            cinemasList = this.getCinemasList(timeTableRepository.findCinemaIdByMovieId(movieId));  
            datesList = this.getDatesList(timeTableRepository.findDateByCinemaIdAndMovieId(cinemaId, movieId));
        }

        if(date != null && movieId != null && cinemaId == null){    // 선택한 날짜&영화의 상영극장
            moviesList = this.getMoviesList(timeTableRepository.findMovieIdByDate(date), group);
            cinemasList = this.getCinemasList(timeTableRepository.findCinemaIdIdByMovieIdAndDate(movieId, date));
            datesList = this.getDatesList(timeTableRepository.findDateByMovieId(movieId));
        }

        if(date != null && cinemaId != null && movieId == null){    // 선택한 날짜&극장의 상영영화
            moviesList = this.getMoviesList(timeTableRepository.findMovieIdByCinemaIdAndDate(cinemaId, date), group);
            cinemasList = this.getCinemasList(timeTableRepository.findCinemaIdIdByDate(date));
            datesList = this.getDatesList(timeTableRepository.findDateByCinemaId(cinemaId));
        }
        
        if(movieId != null && cinemaId != null && date != null){    // 선택한 영화&극장&날짜의 상영시간표
            moviesList = this.getMoviesList(timeTableRepository.findMovieIdByMovieDi(movieId), group);
            cinemasList = this.getCinemasList(timeTableRepository.findCinemaIdIdByMovieIdAndDate(movieId, date));
            datesList = this.getDatesList(timeTableRepository.findDateByCinemaIdAndMovieId(cinemaId, movieId));
            screensList = this.getScreensList(screenRepository.findIdByCinemaId(cinemaId), movieId, date);
        }

        return this.getReturnJsonMap(moviesList, cinemasList, datesList, screensList);
        
    }//end of getScreensInfoMap

    /* 좌석 행별로 분류된 티켓 */
    public Map<String, Object> getSeatsMap(Long timeTableId) throws Exception {

        List<Object> seatsList = new ArrayList<Object>();
        for(String seatRow : seatRepository.findSeatRowsByScreenId(timeTableRepository.findScreenIdById(timeTableId))){
            Map<String, Object> seatsMap = new HashMap<String, Object>();
            List<TicketBySeatDto> ticketsList = new ArrayList<TicketBySeatDto>();
            for(TicketMapping ticket : ticketRepository.findAllByTimeTableIdAndSeatRow(timeTableId, seatRow)){
                ticketsList.add(new TicketBySeatDto(ticket));
            }
            seatsMap.put(seatRow, ticketsList);
            seatsList.add(seatsMap);
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("seats", new Gson().fromJson(new Gson().toJson(seatsList), seatsList.getClass()));

        return returnMap;
    }//end of getSeatsMap

    /* Ticket 상태값 변경 :: 예매 결제 ~ 결제 취소에서 사용 */
    public Map<String, Object> updateTicketState(Map<String, Object> tickets, HttpSession session) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        char ticketState = (String.valueOf(tickets.get("state"))).charAt(0);
        boolean transaction = false;

        /* 테스트용 세션 처리 */
        Account account = new Account();
        account.setAccountId("admin");
        session.setAttribute("account", account);

        /*  
         * Ticket 유효성 체크
         * 요청한 Tickets가 예매가능 상태면 로직 처리 후 1 return
         * 결제대기, 구매완료, 그 외 잘못된 요청일 경우 로직 종류 후 0 return
         * 잘못된 요청에 대비한 Null Check 완료
         */
        if((List)tickets.get("tickets") != null && ticketState == '0'){    //  예매진행
            for(Object ticketId : (List)tickets.get("tickets")){
                if(ticketRepository.findOneById((long)((int)ticketId)).getTicketState() == '1'){
                    transaction = true;
                }else{
                    transaction = false;
                    returnMap.put("result", "500");
                    break;
                }
            }         
        }else if((List)tickets.get("tickets") != null && ticketState == '1'){    // 예매취소
            for(Object ticketId : (List)tickets.get("tickets")){
                if(ticketRepository.findOneById((long)((int)ticketId)).getTicketState() == '0'){
                    transaction = true;
                }else{
                    transaction = false;
                    returnMap.put("result", "500");
                }
            }
        }else{    //  잘못된 요청
            returnMap.put("result", "400");
        }//end of Ticket Validation Check

        /* Ticket 유효성 검사가 모두 완료되고, 안전한 값일 때 DB 로직 실행 */
        if(transaction){    //  정상요청일 경우에 로직 실행
            Ticket ticket = null;
            for(Object ticketId : (List)tickets.get("tickets")){
                ticket = ticketRepository.findOneById((long)((int)ticketId));
                ticket.setTicketState(ticketState);
                if(ticketState == '0'){    //  예매진행 시 토큰 INSERT
                    ticket.setAccountId(((Account)session.getAttribute("account")).getAccountId());
                }else if(ticketState == '1'){ 
                    ticket.setAccountId(null);
                }
                //ticketRepository.save(ticket);
            }
            returnMap.put("result", "200");
        }
        
        return returnMap;
    }//end of updateTicketState

    public Map<String, Object> getUserService(String accountId, HttpSession session) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        String giftCardJson = new Gson().toJson(giftCardRepository.findAllByAccountId(accountId));
        String accountJson = new Gson().toJson(accountRepository.findByAccountId(accountId));
        String movieCouponsJson = new Gson().toJson(movieCouponRepository.findByAccountId(accountId));

        returnMap.put("giftCards", new Gson().fromJson(giftCardJson, List.class));
        returnMap.put("account", new Gson().fromJson(accountJson, Account.class));
        returnMap.put("movieCoupons", new Gson().fromJson(movieCouponsJson, List.class));

        return returnMap;
    }//end of getUserService

    @Transactional
    public Map<String, Object> addPurchase(Map<String, Object> purchaseMap, HttpSession session) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        /* 테스트용 session */
        Account accountTest = new Account();
        accountTest.setAccountId("admin");
        session.setAttribute("account", accountTest);

        if(purchaseMap.containsKey("imp_uid")){
            
            //////////////////////////////////////// IamPortAPI  ////////////////////////////////////////
            //참고 : https://github.com/iamport/iamport-rest-client-java-hc/tree/master/src/main/java/com/siot/IamportRestHttpClientJava
            String API_URL = "https://api.iamport.kr";
            String api_key = iamPortConfig.getApikey();
            String api_secret = iamPortConfig.getApisecret();
            HttpClient client = HttpClientBuilder.create().build();
            Gson gson = new Gson();
        
            /* getToken */
            AuthData authData = new AuthData(api_key, api_secret);	
            String authJsonData = gson.toJson(authData);
            
                StringEntity data = new StringEntity(authJsonData);
                
                HttpPost postRequest = new HttpPost(API_URL+"/users/getToken");
                postRequest.setHeader("Accept", "application/json");
                postRequest.setHeader("Connection","keep-alive");
                postRequest.setHeader("Content-Type", "application/json");
                
                postRequest.setEntity(data);
                
                HttpResponse response = client.execute(postRequest);
                
                if (response.getStatusLine().getStatusCode() != 200) {
                    // throw new RuntimeException("Failed : HTTP error code : "
                    // + response.getStatusLine().getStatusCode());
                    returnMap.put("result", "0");
                    return returnMap;
                }
                
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                Type listType = new TypeToken<IamportResponse<AccessToken>>(){}.getType();
                IamportResponse<AccessToken> auth = gson.fromJson(body, listType);
            
            String token = auth.getResponse().getToken();
           
            /* getPurchase
             * IamPort Server에서 Data 가져오기 : 안전한 데이터
             */
            if(token != null) {
                String path = "/payments/"+purchaseMap.get("imp_uid");

                HttpGet getRequest = new HttpGet(API_URL+path);
                getRequest.addHeader("Accept", "application/json");
                getRequest.addHeader("Authorization", token);

                HttpResponse responses = client.execute(getRequest);

                if (responses.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                    + responses.getStatusLine().getStatusCode());
                }
                
                ResponseHandler<String> handlers = new BasicResponseHandler();
                String responsed = handlers.handleResponse(responses);
                
                Type listTypes = new TypeToken<IamportResponse<Purchase>>(){}.getType();
                IamportResponse<Purchase> purchaseData = gson.fromJson(responsed, listTypes);
                Purchase purchase = purchaseData.getResponse();

                //////////////////////////////////////// IamPortAPI  ////////////////////////////////////////

                String movieCoupons = "";
                String tickets = "";
                String giftCards = "";
                if(purchaseMap.containsKey("movieCoupons") && ((List)purchaseMap.get("movieCoupons")).size() > 0){    //  movieCoupons 유효성 검사
                    for(Object movieCouponId : (List)purchaseMap.get("movieCoupons")){
                        movieCoupons += movieCouponId+",";
                        MovieCoupon movieCoupon = movieCouponRepository.findOneById((long)movieCouponId);
                        movieCoupon.setMovieCouponState('0');
                        movieCouponRepository.save(movieCoupon);
                    }
                    movieCoupons = movieCoupons.substring(0, movieCoupons.length()-1);
                }
                boolean everythingsOk = true;
                if(purchaseMap.containsKey("giftCards") && ((List)purchaseMap.get("giftCards")).size() > 0){    //  giftCards 유효성 검사
                    for(Object giftCardInfo : (List)purchaseMap.get("giftCards")){
                        Map<String, Object> map = (Map)giftCardInfo;
                        giftCards += map.get("giftCardId");
                        GiftCard giftCard = giftCardRepository.findOneById((long)map.get("giftCardId"));
                        int useMoney = (int)map.get("useMoney");
                        int balance = giftCard.getGiftCardBalance();
                        if(balance >= useMoney){
                            giftCard.setGiftCardBalance((balance-useMoney));
                            giftCardRepository.save(giftCard);
                        }else{
                            everythingsOk = false;
                            break;
                        }
                    }
                    giftCards = giftCards.substring(0, giftCards.length()-1);
                }
                if(purchaseMap.containsKey("point") && ((List)purchaseMap.get("point")).size() > 0){    //  point 유효성 검사
                    Account account = accountRepository.findByAccountId(((Account)session.getAttribute("account")).getAccountId());
                    int usePoint = (int)((List)purchaseMap.get("point")).get(0);
                    int point = account.getPoint();
                    if(point >= usePoint){
                        account.setPoint((point-usePoint));
                        accountRepository.save(account);
                    }else{
                        everythingsOk = false;
                    }
                }
                if(purchaseMap.containsKey("tickets") && ((List)purchaseMap.get("tickets")).size() > 0){    //  tickets 유효성 검사
                    for(Object ticketId : (List)purchaseMap.get("tickets")){
                        tickets += ticketId+",";
                        if(everythingsOk){ 
                            Ticket ticket = ticketRepository.findOneById((long)ticketId);
                            ticket.setTicketState('2');
                            ticketRepository.save(ticket);

                        }
                    }
                    tickets = tickets.substring(0, tickets.length()-1);
                }

                if(everythingsOk){    // 기프트카드, 포인트 잔액 유효성 검사에 성공했을 경우
                    paymentRepository.save(
                            Payment.builder().accountId(((Account)session.getAttribute("account")).getAccountId())
                                            .receiverName(purchase.getBuyerName())
                                            .receiverPhone(purchase.getBuyerTel())
                                            .paymentMethod(purchase.getPayMethod())
                                            .paymentAmount(purchase.getAmount())
                                            .giftCardIds(giftCards)
                                            .movieCouponIds(movieCoupons)
                                            .ticketIds(tickets)
                                            .build()
                    );

                    returnMap.put("result", "1");
                }else{
                    returnMap.put("result", "0");    // 기프트카드, 포인트 잔액조회 검사에서 실패했을 경우
                }
                //end of getPurchase
            }else{
                returnMap.put("result", "0");    // IamPort Server에 해당 Data가 없을 경우
            }//end of token Validation Check
        }else{
            returnMap.put("result", "0");    // imp_uid가 Map에 없을 경우
        }//end of imp_uid Validation Check
    
        return returnMap;
    }//end of addPurchase

    /**
     * 모든 영화관을 지역별로 분류하고, 검색조건과 일치하는지 true/false 값을 주는 로직 
     * @param cinemaIdsList    검색조건에 맞는 극장 고유번호 리스트
     * @return    지역별 극장 리스트
     */
    public List<Map<String, Object>> getCinemasList(List<Long> cinemaIdsList) throws Exception {

        List<Map<String, Object>> cinemasList = new ArrayList< Map<String, Object>>();
        for(String cinemaArea : cinemaRepository.findCinemaArea()){
            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea)){
                    CinemaDto cinemaDto = new CinemaDto(cinema);              
                    if(cinemaIdsList == null || cinemaIdsList.contains(cinemaDto.getId())){    // 해당 영화관이 검색조건과 일치하는 경우
                        cinemaDto.setIsVailable(true);
                    }
                    cinemaList.add(cinemaDto);
            }
            Map<String, Object> cinemasMap = new HashMap<String, Object>();
            cinemasMap.put("cinemaArea", cinemaArea);
            cinemasMap.put("cinemaList", cinemaList);
            cinemasList.add(cinemasMap);
        }

        return cinemasList;

    }//end of getCinemasList

    /**
     * 정렬된 모든 영화를 검색조건과 비교하여 true/false 값을 주는 로직
     * @param movieIdsList    검색조건에 만족하는 영화 고유번호 리스트
     * @param group    정렬조건
     * @return    정렬되고 검색비교가 완료된 영화 리스트
     */
    public List<MovieDto> getMoviesList(List<Long> movieIdsList, String group) throws Exception {
        
        List<MovieMapping> sortedMovieList = null;    // 정렬조건에 만족하는 모든 영화 리스트
        if(group.equals("0")){    //  0 : 예매율순
            sortedMovieList = movieRepository.findAllByOrderByReservationRateDesc(MovieMapping.class);    
        }else{    //  1 : 가나다순
            sortedMovieList = movieRepository.findAllByOrderByMovieTitleAsc(MovieMapping.class);    
        }

        List<MovieDto> moviesList = new ArrayList<MovieDto>();
        for(MovieMapping movie : sortedMovieList){
            MovieDto movieDto = new MovieDto(movie);
            boolean same = (movieIdsList == null || movieIdsList.contains(movieDto.getId())) ? true : false;
            movieDto.setIsVailable(same);
            moviesList.add(movieDto);
        }

        return moviesList;

    }//end of getMoviesList

    /* 객체를 JSON으로 바꿔서 return */
    public Map<String, Object> getReturnJsonMap(List<MovieDto> moviesList, List<Map<String, Object>> cinemasList, 
            List<Map<String, Object>> datesList, List<Map<String, Object>> screensList) throws Exception {

        Map<String, Object> returnJsonMap = new HashMap<String, Object>();
        if(cinemasList != null){   
            returnJsonMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
        }
        if(moviesList != null){
            returnJsonMap.put("movies", new Gson().fromJson(new Gson().toJson(moviesList), moviesList.getClass()));
        }
        if(datesList != null){
            returnJsonMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
        }
        if(screensList != null){
            returnJsonMap.put("showtimes", new Gson().fromJson(new Gson().toJson(screensList), screensList.getClass()));
        }

        return  returnJsonMap;

    }//end of getReturnJsonMap

    /**
     * @param selectionList
     * @return
     */
    public List<Map<String, Object>> getDatesList(List<Long> selectionList) throws Exception {

        List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
        for(Long dates : timeTableRepository.findDate()){
            Map<String, Object> map = new HashMap<String, Object>();
            boolean same = (selectionList == null || selectionList.contains(dates)) ? true : false;
            map.put("date", dates);
            map.put("isVailable", same);
            datesList.add(map);
        }

        return datesList;

    }//end of getSelectionList

    /* 극장별 날짜,영화에 해당되는 상영 시간표 */
    public List<Map<String, Object>> getScreensList(List<Long> screenIdList, Long movieId, Long date) throws Exception {

        List<Map<String, Object>> screensList = new ArrayList<Map<String, Object>>();
        for(Long screenId : screenIdList){
            List<TimeTableDto> lists = new ArrayList<TimeTableDto>();;
            for(TimeTable timeTable : timeTableRepository.findTimeTableByMovieIdAndScreenIdAndDate(movieId, screenId, date)){
                TimeTableDto timeTableDto = new TimeTableDto(timeTable);
                timeTableDto.setEmptySeat(ticketRepository.findAllByTimeTableId(timeTable.getId()).size());
                lists.add(timeTableDto);
            }

            if(lists.size() > 0){
                Map<String, Object> screensMap = new HashMap<String, Object>();
                screensMap.put("screen", screenRepository.findOneById(screenId));
                screensMap.put("timeTables", lists);
                screensList.add(screensMap);
            }
        }  
        return screensList;

    }

}//end of class
