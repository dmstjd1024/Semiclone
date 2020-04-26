package com.semiclone.springboot.service.ticket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.seat.SeatRepository;
import com.semiclone.springboot.domain.ticket.Ticket;
import com.semiclone.springboot.domain.ticket.TicketRepository;
import com.semiclone.springboot.domain.timetable.TimeTable;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.web.dto.CinemaDto;
import com.semiclone.springboot.web.dto.MovieDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service("ticketServiceImpl")
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    //Field
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final TimeTableRepository timeTableRepository;
    private final ScreenRepository screenRepository;
    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;

    //Method
    /* 모든 영화, 극장, 날짜 리스트 return */
    public Map<String, Object> getScreensMap() throws Exception{        
        Map<String, Object> returnMap = new HashMap<String, Object>();

        /* Moives */
        List<MovieDto> movieList = new ArrayList<MovieDto>();
        for(Movie obj : movieRepository.findAll()){
            movieList.add(new MovieDto(obj));
        }
        String moviesJson = new Gson().toJson(movieList);

        /* Cinemas */
        List<Object> cinemasList = new ArrayList<Object>();
        for(Object cinemaArea : cinemaRepository.findCinemaArea()){

            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            Map<String, Object> cinemasMap = new HashMap<String, Object>();
            for(Object obj : cinemaRepository.findAllByCinemaArea(cinemaArea.toString())){
                cinemaList.add(new CinemaDto((Cinema)obj));
            }
            String cinemasJson = new Gson().toJson(cinemaList);

            cinemasMap.put("cinemaArea", cinemaArea.toString());
            cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
            cinemasList.add(cinemasMap);
        }

        /* Dates */
        List<Long> datesList = new ArrayList<Long>();
        for(Long obj : timeTableRepository.findDate()){
            datesList.add(obj);
        }
        String datesJson = new Gson().toJson(datesList);

        returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
        returnMap.put("cinemas", cinemasList);
        returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
       
        return returnMap;
    }//end of getScreensMap

    public Map<String, Object> getScreensInfoMap(Long movieId, Long cinemaId, Long date, Long timeTableId, String group) throws Exception{

        /* Test용 로직 :: Swagger UI에서 Param값에 null 지원을 안하므로, 로직으로 null 처리*/
        if(movieId == 123890){
            movieId = null;
        }
        if(cinemaId == 123890){
            cinemaId = null;
        }
        if(date == 123890){
            date = null;
        }
        if(timeTableId == 123890){
            timeTableId = null;
        }
        if(group.equals("123890")){
            group = "0";
        }

        /* 모든 Param이 null일 때 전체값 return */
        if(movieId == null && cinemaId == null && date == null && timeTableId == null && group.equals("")){
            return this.getScreensMap();
        }

        /* 정렬방법 세팅 */
        if(group.equals("") || group == null || group.equals("0")){
            group = "0";    //  0 : 예매율순
        }else{
            group = "1";    //  1 : 가나다순
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        /* 영화만 선택했을 때 */ 
        if(movieId != null && cinemaId == null && date == null && timeTableId == null){

            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByMovieId(movieId)){
                Long id = screenRepository.findCinemaIdById(obj);
                if(!list.contains(id)){
                    list.add(id);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        cinemaList.add(new CinemaDto((Cinema)obj));
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
                cinemasList.add(cinemasMap);
            }

            /* Dates */
            List<Long> datesList = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findDateByMovieId(movieId)){
                datesList.add(obj);
            }
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }

        /* 극장만 선택했을 때 */
        if(movieId == null && cinemaId != null && date == null && timeTableId == null){
            /* Moives */
            List<Long> movieIdList = new ArrayList<Long>();
            List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);
            for(Long screenId : screenIdList){
                for(Long id : timeTableRepository.findMovieIdByScreenId(screenId)){
                    if(!movieIdList.contains(id)){
                        movieIdList.add(id);
                    }
                }
            }
            movieIdList.sort(null);
            
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Long id : movieIdList){
                movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

            /* Dates */
            List<Long> datesList = new ArrayList<Long>();
            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenId(id)){
                    if(!datesList.contains(dates)){
                        datesList.add(dates);
                    }
                }
            }
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }

        /* 날짜만 선택했을 때 */
        if(movieId == null && cinemaId == null && date != null && timeTableId == null){
            /* Moives */
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Long id : timeTableRepository.findMovieIdByDate(date)){
                    movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByDate(date)){
                Long id = screenRepository.findCinemaIdById(obj);
                if(!list.contains(id)){
                    list.add(id);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        cinemaList.add(new CinemaDto((Cinema)obj));
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
                cinemasList.add(cinemasMap);
            }

            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("cinemas", cinemasList);
        }

        /* 영화, 극장이 선택되었을 때 */
        if(movieId != null && cinemaId != null && date == null && timeTableId == null){
            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByMovieId(movieId)){
                Long id = screenRepository.findCinemaIdById(obj);
                if(!list.contains(id)){
                    list.add(id);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        cinemaList.add(new CinemaDto((Cinema)obj));
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
                cinemasList.add(cinemasMap);
            }

            /* Moives */
            List<Long> movieIdList = new ArrayList<Long>();
            List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);
            for(Long screenId : screenIdList){
                for(Long id : timeTableRepository.findMovieIdByScreenId(screenId)){
                    if(!movieIdList.contains(id)){
                        movieIdList.add(id);
                    }
                }
            }
            movieIdList.sort(null);
            
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Long id : movieIdList){
                movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

            /* Dates */
            List<Long> datesList = new ArrayList<Long>();
            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenIdAndMovieId(id, movieId)){
                    if(!datesList.contains(dates)){
                        datesList.add(dates);
                    }
                }
            }
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }

        /* 영화, 날짜가 선택되었을 때 */
        if(movieId != null && cinemaId == null && date != null && timeTableId == null){
            /* Moives */
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Long id : timeTableRepository.findMovieIdByDate(date)){
                    movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

            /* Dates */
            List<Long> datesList = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findDateByMovieId(movieId)){
                datesList.add(obj);
            }
            String datesJson = new Gson().toJson(datesList);

            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByMovieIdAndDate(movieId, date)){
                Long id = screenRepository.findCinemaIdById(obj);
                if(!list.contains(id)){
                    list.add(id);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        cinemaList.add(new CinemaDto((Cinema)obj));
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
                cinemasList.add(cinemasMap);
            }

            returnMap.put("cinemas", cinemasList);
            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }

        /* 극장, 날짜가 선택되었을 때 */
        if(movieId == null && cinemaId != null && date != null && timeTableId == null){

            /* Moives */
            List<Long> movieIdList = new ArrayList<Long>();
            List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);
            for(Long screenId : screenIdList){
                for(Long id : timeTableRepository.findMovieIdByScreenIdAndDate(screenId, date)){
                    if(!movieIdList.contains(id)){
                        movieIdList.add(id);
                    }
                }
            }
            movieIdList.sort(null);
            
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Long id : movieIdList){
                movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByDate(date)){
                Long id = screenRepository.findCinemaIdById(obj);
                if(!list.contains(id)){
                    list.add(id);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        cinemaList.add(new CinemaDto((Cinema)obj));
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
                cinemasList.add(cinemasMap);
            }

            /* Dates */
            List<Long> datesList = new ArrayList<Long>();
            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenId(id)){
                    if(!datesList.contains(dates)){
                        datesList.add(dates);
                    }
                }
            }
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }
        
        /* 영화, 극장, 날짜가 선택되었을 때 */
        if(movieId != null && cinemaId != null && date != null && timeTableId == null){
            /* Moives */
            List<Long> movieIdList = new ArrayList<Long>();
            List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);
            for(Long screenId : screenIdList){
                for(Long id : timeTableRepository.findMovieIdByScreenIdAndDate(screenId, date)){
                    if(!movieIdList.contains(id)){
                        movieIdList.add(id);
                    }
                }
            }
            movieIdList.sort(null);
            
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Long id : movieIdList){
                movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByMovieIdAndDate(movieId, date)){
                Long id = screenRepository.findCinemaIdById(obj);
                if(!list.contains(id)){
                    list.add(id);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        cinemaList.add(new CinemaDto((Cinema)obj));
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
                cinemasList.add(cinemasMap);
            }

            /* Dates */
            List<Long> datesList = new ArrayList<Long>();
            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenIdAndMovieId(id, movieId)){
                    if(!datesList.contains(dates)){
                        datesList.add(dates);
                    }
                }
            }
            String datesJson = new Gson().toJson(datesList);

            /* TimeTables */
            List<TimeTable> timeTableList = new ArrayList<TimeTable>();
            for(Long id : screenIdList){
                for(TimeTable obj : timeTableRepository.findTimeTableByMovieIdAndScreenIdAndDate(movieId, id, date)){
                    timeTableList.add(obj);
                }
            }
            String timeTablesJson = new Gson().toJson(timeTableList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
            returnMap.put("timeTables", new Gson().fromJson(timeTablesJson, timeTableList.getClass()));
        }

        return returnMap;
    }//end of getScreensInfoMap

    public Map<String, Object> getSeatsMap(Long timeTableId) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
       
        List<Ticket> ticketsList = ticketRepository.findAllByTimeTableId(timeTableId);
        List<Object> list = new ArrayList<Object>();
        for(Ticket ticket : ticketsList){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("ticket", ticket);
            map.put("seat", seatRepository.findOneById(ticket.getSeatId()));
            list.add(map);
        }
        String screensJson = new Gson().toJson(list);
        returnMap.put("seats", new Gson().fromJson(screensJson, list.getClass()));

        return returnMap;
    }//end of getSeatsMap

    /* Ticket 상태값 변경 :: 예매 결제 ~ 결제 취소에서 사용 */
    public Map<String, Object> updateTicketState(Map<String, Object> tickets) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        char ticketState = ((String)tickets.get("state")).charAt(0);
        boolean transaction = false;
        String ticketToken = null;
        List ticketsList = null;
        
        /*  
         * Ticket 유효성 체크
         * 요청한 Tickets가 예매가능 상태면 로직 처리 후 1 return
         * 결제대기, 구매완료, 그 외 잘못된 요청일 경우 로직 종류 후 0 return
         * 잘못된 요청에 대비한 Null Check 완료
         */
        if(ticketState == '1'){    //  예매진행
            if((List)tickets.get("tickets") != null){    //  ticketId Null Check
                for(Object ticketId : (List)tickets.get("tickets")){
                    if(ticketRepository.findOneById((long)((int)ticketId)).getTicketState() == '0'){
                        transaction = true;
                    }else{    //  
                        transaction = false;
                        returnMap.put("result", "0");
                        break;
                    }
                }         
            }else{    //  ticketId 값이 없을 경우
                transaction = false;
                returnMap.put("result", "0");
            }
        }else if(ticketState == '0'){    //  예매취소
            if(tickets.get("ticketTokens") != null){    //  토큰 유효성 검사
                ticketsList = new ArrayList();
                for(Object token : (List)tickets.get("ticketTokens")){
                    List list = ticketRepository.findIdByTicketToken((String)token);
                    if(list.size() != 0){    //  ticketId 유효성 체크
                        ticketsList.add(list.get(0));
                    }else{    //  ticketId 값이 없을 경우
                        break;
                    }
                }
                if(ticketsList.size() > 0){    //  tickets List Null Check
                    tickets.put("tickets", ticketsList);
                    transaction = true;
                }else{    //  tickets Lis 값이 없을 경우
                    returnMap.put("result", "0");
                    transaction = false;
                }
            }
        }else{    //  잘못된 요청
            returnMap.put("result", "0");
        }//end of Ticket Validation Check

        /* Ticket 유효성 검사가 모두 완료되고, 안전한 값일 때 DB 로직 실행 */
        if(transaction){    //  정상요청일 경우에 로직 실행
            Ticket ticket = null;
            List<String> ticketTokens = new ArrayList<String>();
            for(Object ticketId : (List)tickets.get("tickets")){
                ticket = ticketRepository.findOneById((long)((int)ticketId));

                ticket.setTicketState(ticketState);
                if(ticketState == '1'){    //  예매진행 시 토큰 INSERT
                    ticketToken = (new Random().nextInt(99999)+100000)+""+System.currentTimeMillis();
                    ticketTokens.add(ticketToken);
                    ticket.setTicketToken(ticketToken);
                }else if(ticketState == '0'){    //  예매취소 시 토큰 DELETE
                    ticket.setTicketToken(null);
                }
                ticketRepository.save(ticket);
            }
            returnMap.put("result", "1");
            returnMap.put("ticketTokens", ticketTokens);    //  결제 취소를 진행할 때, ticketId 대신에 ticketToken 사용
        }
        
        return returnMap;
    }//end of updateTicketState

    public Map<String, Object> addPurchase(Map<String, Object> purchase) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        // boolean everythingsFine = false;
		// 	String API_URL = "https://api.iamport.kr";
		// 	String imp_key = "";
		// 	String imp_secret = "";
			
		// 	//Get AccessToken
		// 	DefaultHttpClient httpClient = new DefaultHttpClient();
		// 	String url = "https://api.iamport.kr";
		// 	HttpPost httpPost = new HttpPost(API_URL+"/users/getToken");
		// 	httpPost.setHeader("Accept", "application/json");
		// 	httpPost.setHeader("Connection","keep-alive");
		// 	httpPost.setHeader("Content-Type", "application/json");
			
		// 	ObjectMapper objectMapper = new ObjectMapper();
		// 	AuthData authData = new AuthData(imp_key, imp_secret);
		// 	String data = objectMapper.writeValueAsString(authData);
		// 	StringEntity httpEntity = new StringEntity(data);
		// 	httpPost.setEntity(httpEntity);

		// 	org.apache.http.HttpResponse httpResponse = httpClient.execute(httpPost);
		// 	HttpEntity responseHttpEntity = httpResponse.getEntity();
		// 	InputStream is = responseHttpEntity.getContent();
		// 	BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));

		// 	String temp="";
		// 	String response="";
		// 	while( (temp = br.readLine()) != null) {
		// 		response += temp;
		// 	}
		// 	JSONObject jsonObj = (JSONObject)JSONValue.parse(((JSONObject)JSONValue.parse(response)).get("response").toString());
		// 	String token  = jsonObj.get("access_token").toString();
			
		// 	//Get Payment
		// 	DefaultHttpClient paymentHttpClient = new DefaultHttpClient();
		// 	String paymentUrl = API_URL+"/payments/"+purchase.get("imp_uid");
		// 	HttpGet paymentHttpGet = new HttpGet(paymentUrl);
		// 	paymentHttpGet.addHeader("Accept", "application/json");
		// 	paymentHttpGet.addHeader("Authorization", token);
			
		// 	HttpResponse paymentHttpResponse = (HttpResponse)paymentHttpClient.execute(paymentHttpGet);
		// 	HttpEntity paymentHttpEntity = paymentHttpResponse.getEntity();
		// 	InputStream paymentIs = paymentHttpEntity.getContent();
		// 	BufferedReader paymentBr = new BufferedReader(new InputStreamReader(paymentIs,"UTF-8"));
			
		// 	// check everythings_fine
		// 	if (paymentHttpResponse.getStatusLine().getStatusCode() != 200) {
		// 		throw new RuntimeException("Failed : HTTP error code : "
		// 		   + paymentHttpResponse.getStatusLine().getStatusCode());
		// 	}
			
		// 	String paymentTemp="";
		// 	String paymentResponse="";
		// 	while( (paymentTemp = paymentBr.readLine()) != null) {
		// 		paymentResponse+= paymentTemp;
		// 	}
		// 	JSONObject jsonObj11 = (JSONObject)JSONValue.parse(paymentResponse);
		// 	ObjectMapper finalObjectMapper = new ObjectMapper();
		// 	HashMap paymentMap = finalObjectMapper.readValue(jsonObj11.get("response").toString(), HashMap.class);
		// 	String payMethod = paymentMap.get("pay_method").toString();
			
		// 	if( payMethod.equals("card") ) {
		// 		payMethod = "0";
		// 	}else if( payMethod.equals("phone") ) {
		// 		payMethod = "1";
		// 	}
			// purchase.setPayMethod(payMethod);
			// purchase.setPurchasePrice((Integer)paymentMap.get("amount"));
			// System.out.println(purchase.toString());
			
			// User user = new User();
			// user.setUserId(((User)session.getAttribute("user")).getUserId());
			// purchase.setUser(user);
				
			// if( purchaseDAO.addPayment(purchase) == 1) {
			// 	everythingsFine = true;
			// }

			// return everythingsFine;

        return returnMap;
    }//end of addPurchase

}//end of class
