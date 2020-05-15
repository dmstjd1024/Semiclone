package com.semiclone.springboot;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.MovieMapping;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.payment.Payment;
import com.semiclone.springboot.domain.payment.PaymentRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.seat.SeatRepository;
import com.semiclone.springboot.domain.ticket.Ticket;
import com.semiclone.springboot.domain.ticket.TicketRepository;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.web.dto.CinemaDto;
import com.semiclone.springboot.web.dto.MovieDetailDto;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketRestControllerTest {

    @Autowired
    private TimeTableRepository timeTableRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private SeatRepository seatRepository;

    //@Test
    public void joinQueryTest() throws Exception {
        
        /* movieId로 cinemaId 얻는 로직 */
        List<Long> list = new ArrayList<Long>();
        // for(Long obj : timeTableRepository.findScreenIdByMovieId((long)1)){
        //     Long cinemaId = screenRepository.findCinemaIdById(obj);
        //     if(!list.contains(cinemaId)){
        //         list.add(cinemaId);
        //     }
        // }
        list.sort(null);    //  리스트 정렬
    
        /* Cinemas */
        List<Object> cinemasList = new ArrayList<Object>();
        for(Object cinemaArea : cinemaRepository.findCinemaArea()){

            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            Map<String, Object> cinemasMap = new HashMap<String, Object>();
            for(Long cinemaId : list){
                for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cinemaId)){
                    cinemaList.add(new CinemaDto((Cinema)obj));
                }
            }
            String cinemasJson = new Gson().toJson(cinemaList);

            cinemasMap.put("cinemaArea", cinemaArea.toString());
            cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
            cinemasList.add(cinemasMap);
        }  

    }//end of joinQueryTest 

    //@Test
    public void cinemaOnlytest() throws Exception{
        /* Moives */
        List<Long> movieIdList = new ArrayList<Long>();
        List<Long> screenIdList = screenRepository.findIdByCinemaId((long)4);
        for(Long screenId : screenRepository.findIdByCinemaId((long)4)){

            // for(Long id : timeTableRepository.findMovieIdByScreenId(screenId)){
            //     if(!movieIdList.contains(id)){
            //         movieIdList.add(id);
            //     }
            // }
        }
        movieIdList.sort(null);
        
        List<MovieDetailDto> movieList = new ArrayList<MovieDetailDto>();
        for(Long id : movieIdList){
            movieList.add(new MovieDetailDto(movieRepository.findOneById(id)));
        }
        String moviesJson = new Gson().toJson(movieList);

        /* Dates */
        List<Long> datesList = new ArrayList<Long>();
        for(Long id : screenIdList){
            // for(Long dates : timeTableRepository.findDateByScreenId(id)){
            //     if(!datesList.contains(dates)){
            //         datesList.add(dates);
            //     }
            // }
        }
        String datesJson = new Gson().toJson(datesList);
        System.out.println(moviesJson);
        System.out.println(datesJson);
    }

    //@Test
    public void dateOnlyTest(){
        /* Moives */
        List<MovieDetailDto> movieList = new ArrayList<MovieDetailDto>();
        for(Long id : timeTableRepository.findMovieIdByDate((long)20200422)){
                movieList.add(new MovieDetailDto(movieRepository.findOneById(id)));
        }
        String moviesJson = new Gson().toJson(movieList);

        List<Long> list = new ArrayList<Long>();
            // for(Long obj : timeTableRepository.findScreenIdByDate((long)20200422)){
            //     Long id = screenRepository.findCinemaIdById(obj);
            //     if(!list.contains(id)){
            //         list.add(id);
            //     }
            // }
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
            System.out.println(moviesJson);
            for(Object obj : cinemasList){
                System.out.println(obj);
            }
    }

    //@Test
    public void movieAndScreenSelect() throws Exception{

        Long movieId = (long)58;
        Long date = (long)20200423;
        Long cinemaId = (long)29;

        /* Moives */
        List<Long> movieIdList = new ArrayList<Long>();
        List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);
        for(Long screenId : screenIdList){
            // for(Long id : timeTableRepository.findMovieIdByScreenIdAndDate(screenId, date)){
            //     if(!movieIdList.contains(id)){
            //         movieIdList.add(id);
            //     }
            // }
        }
        movieIdList.sort(null);
        
        List<MovieDetailDto> movieList = new ArrayList<MovieDetailDto>();
        for(Long id : movieIdList){
            movieList.add(new MovieDetailDto(movieRepository.findOneById(id)));
        }
        String moviesJson = new Gson().toJson(movieList);

        /* Cinemas */
        List<Long> list = new ArrayList<Long>();
        // for(Long obj : timeTableRepository.findScreenIdByMovieIdAndDate(movieId, date)){
        //     Long id = screenRepository.findCinemaIdById(obj);
        //     if(!list.contains(id)){
        //         list.add(id);
        //     }
        // }
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
            // for(Long dates : timeTableRepository.findDateByScreenIdAndMovieId(id, movieId)){
            //     if(!datesList.contains(dates)){
            //         datesList.add(dates);
            //     }
            // }
        }
        String datesJson = new Gson().toJson(datesList);

        for(Object obj : cinemasList){
            System.out.println(obj);
        }
        System.out.println(moviesJson);
        System.out.println(datesJson);
        
    }

    //@Test
    public void findOneByIdTest() throws Exception {

        Long timeTableId = (long)1;
        Long screenId = timeTableRepository.findOneById(timeTableId).getScreenId();  
        System.out.println("\n"+screenId+"\n");

        List<Ticket> ticketList = ticketRepository.findAllByTimeTableId(timeTableId);
        for(Ticket ticket : ticketList){
            System.out.println(ticket.getId());
        }

    }

    //@Test
    public void queryTest() throws Exception {

        Long cinemaId = (long)1;
        //System.out.println(cinemaRepository.findCinemaAreaById(cinemaId));
        System.out.println(cinemaRepository.findCinemaNameById(cinemaId));

    }

    //@Test
    public void updateTest() throws Exception {
        Long ticketId = (long)1;
        Ticket ticket = ticketRepository.findOneById(ticketId);
        ticket.setTicketState('1');
        System.out.println(ticketRepository.save(ticket));
    }

    //@Test
    public void HttpClientTest() throws Exception {

        String API_URL = "https://api.iamport.kr";
        String api_key = "9412212325207428";
        String api_secret = "PMPPj0OrmSrYllpBBnqN3ofIGu07GgYv3OlmOaOa2g9oCyZeVp1ZOhIuLICrZBX3pipdseovvxT76Tm2";
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
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
			
			ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);
			Type listType = new TypeToken<IamportResponse<AccessToken>>(){}.getType();
            IamportResponse<AccessToken> auth = gson.fromJson(body, listType);
        
        String token = auth.getResponse().getToken();
        
        
		if(token != null) {
			String path = "/payments/"+"imp_541789576970";

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
			IamportResponse<Purchase> paymentData = gson.fromJson(responsed, listTypes);
            Purchase purchase = paymentData.getResponse();

            System.out.println("\n=================================================");
            System.out.println("buyerName : "+purchase.getBuyerName());
            System.out.println("buyerTel : "+purchase.getBuyerTel());
            System.out.println("payMethod : "+purchase.getPayMethod());
            System.out.println("amount : "+purchase.getAmount());
            System.out.println("status : "+purchase.getStatus());
            System.out.println("=================================================\n");


       }
    }

    //@Test
    public void rownumTest() throws Exception {
        String sort = "reservation_rate";
        // List<Movie> moviesList = movieRepository.findMoviesRankBySort(sort);
        // for(Movie movie : moviesList){
        //     System.out.println(movie);
        // }
    }

    //@Test
    public void mapContainsTest() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", "test");
        map.put("test1", "test1");
        System.out.println("\nmap.containsKey(\"key\") Test Result");
        System.out.println("Key=test : "+map.containsKey("test"));
        System.out.println("Key=null : "+map.containsKey("null"));
        System.out.println("Test End\n");
    }

    //@Test
    public void dateTest() throws Exception {
        // System.out.println("\n===========================================");
        // System.out.println(new Date(System.currentTimeMillis()));
        // Payment payment = Payment.builder().accountId("test").paymentStatus("1234")
        // .depositeBank("bank").depositorName("name").paymentMethod("card")
        // .paymentAmount(1000).receiverName("123").receiverPhone("01012341234").build();
        // paymentRepository.save(payment);
    }

    //@Test
    public void paymentTest() throws Exception {
        String giftCards = "";
        String movieCoupons = "";
        String tickets = "";
        Payment payment = Payment.builder().accountId("1").receiverName("1").receiverPhone("1").paymentMethod("1")
                        .paymentAmount(BigDecimal.valueOf(1)).giftCardIds(giftCards)
                        .movieCouponIds(movieCoupons).ticketIds(tickets).build();
        paymentRepository.save(payment);
    }

    //@Test
    public void nativeQueryTest() throws Exception {
        List<MovieMapping> list = movieRepository.findAllBy(MovieMapping.class);
        for(MovieMapping abc : list){
            System.out.println(abc.getId());
        }
        //System.out.println(movieRepository.findOneByMovieTitle("킹덤").getMovieTitle());
    }

    //@Test
    public void timerTest() throws Exception {
        Timer timer = new Timer();
        TimerTask t = new TimerTask(){
        
            @Override
            public void run() {
                System.out.println("Hello, World");
                
            }
        };
        System.out.println("테스트 시작");

        timer.schedule(t, 1000);
        Thread.sleep(100);
    }

    

}