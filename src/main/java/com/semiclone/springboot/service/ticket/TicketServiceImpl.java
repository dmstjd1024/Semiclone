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
import com.semiclone.springboot.domain.ticket.TicketRepository;
import com.semiclone.springboot.domain.tickethistory.TicketHisotryRepository;
import com.semiclone.springboot.domain.tickethistory.TicketHistory;
import com.semiclone.springboot.domain.timetable.TimeTable;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.web.dto.CinemaDto;
import com.semiclone.springboot.web.dto.MovieDto;
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
    /* 모든 영화, 극장, 날짜 리스트 return */
    public Map<String, Object> getScreensMap() throws Exception{        
        /* Moives */
        List<MovieDto> moviesList = new ArrayList<MovieDto>();
        for(MovieMapping movie : movieRepository.findAllByOrderByReservationRateDesc(MovieMapping.class)){
            MovieDto movieDto = new MovieDto(movie);
            movieDto.setIsVailable(true);
            moviesList.add(movieDto);
        }
        
        /* Cinemas */
        List<Object> cinemasList = new ArrayList<Object>();
        for(String cinemaArea : cinemaRepository.findCinemaArea()){
            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea)){
                CinemaDto cinemaDto = new CinemaDto(cinema);
                cinemaDto.setIsVailable(true);
                cinemaList.add(cinemaDto);
            }
            Map<String, Object> cinemasMap = new HashMap<String, Object>();
            cinemasMap.put("cinemaArea", cinemaArea);
            cinemasMap.put("cinemaList", cinemaList);
            cinemasList.add(cinemasMap);
        }

        /* Dates */
        List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
        for(Long date : timeTableRepository.findDate()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", date);
            map.put("isVailable", true);
            datesList.add(map);
        }
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("movies", new Gson().fromJson(new Gson().toJson(moviesList), moviesList.getClass()));
        returnMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
        returnMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
       
        return returnMap;
    }//end of getScreensMap

    public Map<String, Object> getScreensInfoMap(Long movieId, Long cinemaId, Long date, String group) throws Exception{
        
        /* Test용 로직 :: Swagger UI에서 Param값에 null 지원을 안하므로, 로직으로 null 처리*/
        if(movieId == null || movieId == 123890){
            movieId = null;
        }
        if(cinemaId  == null || cinemaId == 123890){
            cinemaId = null;
        }
        if(date  == null || date == 123890){
            date = null;
        }
        if(group.equals("123890")){
            group = "0";
        }

        /* 모든 Param이 null일 때 전체값 return */
        if(movieId == null && cinemaId == null && date == null && group.equals("")){
            return this.getScreensMap();
        }

        /* 정렬방법 세팅 */
        List<MovieMapping> movieOrderList = null;
        if(group.equals("") || group == null || group.equals("0")){
            movieOrderList = movieRepository.findAllByOrderByReservationRateDesc(MovieMapping.class);    //  0 : 예매율순
        }else{
            movieOrderList = movieRepository.findAllByOrderByMovieTitleAsc(MovieMapping.class);    //  1 : 가나다순
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        /* 영화만 선택했을 때 */ 
        if(movieId != null && cinemaId == null && date == null){

            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long screenId : timeTableRepository.findScreenIdByMovieId(movieId)){
                Long cineId = screenRepository.findCinemaIdById(screenId);
                if(!list.contains(cineId)){
                    list.add(cineId);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<Object> cinemasList = new ArrayList<Object>();
            for(String cinemaArea : cinemaRepository.findCinemaArea()){
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea)){
                        CinemaDto cinemaDto = new CinemaDto(cinema);
                        if(list.contains(cinemaDto.getId())){
                            cinemaDto.setIsVailable(true);
                        }
                        cinemaList.add(cinemaDto);
                }
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                cinemasMap.put("cinemaArea", cinemaArea);
                cinemasMap.put("cinemaList", cinemaList);
                cinemasList.add(cinemasMap);
            }

            /* Dates */
            List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
            List<Long> listed = timeTableRepository.findDateByMovieId(movieId);
            for(Long dates : timeTableRepository.findDate()){
                boolean same = listed.contains(dates) ? true : false ;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", dates);
                map.put("isVailable", same);
                datesList.add(map);
            }

            returnMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
            returnMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
        }

        /* 극장만 선택했을 때 */
        if(movieId == null && cinemaId != null && date == null){
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
            
            List<MovieDto> moviesList = new ArrayList<MovieDto>();
            for(MovieMapping movie : movieOrderList){
                moviesList.add(new MovieDto(movie));
            }

            for(Long id : movieIdList){
                for(MovieDto movieDto : moviesList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }
            }

            /* Dates */
            List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
            for(Long dates : timeTableRepository.findDate()){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", dates);
                map.put("isVailable", false);
                datesList.add(map);
            }
            
            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenId(id)){
                    for(Map<String, Object> map : datesList){
                        if((long)map.get("date") == dates){
                            map.replace("isVailable", false, true);
                        }
                    }
                }
            }

            returnMap.put("movies", new Gson().fromJson(new Gson().toJson(moviesList), moviesList.getClass()));
            returnMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
        }

        /* 날짜만 선택했을 때 */
        if(movieId == null && cinemaId == null && date != null){
            /* Moives */
            List<MovieDto> moviesList = new ArrayList<MovieDto>();
            for(MovieMapping movie : movieOrderList){
                moviesList.add(new MovieDto(movie));
            }

            for(Long id : timeTableRepository.findMovieIdByDate(date)){
                for(MovieDto movieDto : moviesList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }
            }

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
            for(String cinemaArea : cinemaRepository.findCinemaArea()){
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea)){
                        CinemaDto cinemaDto = new CinemaDto(cinema);
                        for(Long cineId : list){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea) && (cinemaDto.getId() == cineId)){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        cinemaList.add(cinemaDto);
                }
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                cinemasMap.put("cinemaArea", cinemaArea);
                cinemasMap.put("cinemaList", cinemaList);
                cinemasList.add(cinemasMap);
            }

            returnMap.put("movies", new Gson().fromJson(new Gson().toJson(moviesList), moviesList.getClass()));
            returnMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
        }

        /* 영화, 극장이 선택되었을 때 */
        if(movieId != null && cinemaId != null && date == null){
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
                for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea.toString())){
                        CinemaDto cinemaDto = new CinemaDto(cinema);
                        for(Long cineId : list){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && (cinemaDto.getId() == cineId)){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        cinemaList.add(cinemaDto);
                }
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", cinemaList);
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
            for(MovieMapping movie : movieOrderList){
                movieList.add(new MovieDto(movie));
            }

            for(Long id : movieIdList){
                for(MovieDto movieDto : movieList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }
            }

            /* Dates */
            List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
            for(Long dates : timeTableRepository.findDate()){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", dates);
                map.put("isVailable", false);
                datesList.add(map);
            }

            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenIdAndMovieId(id, movieId)){
                    for(Map<String, Object> map : datesList){
                        if((long)map.get("date") == dates){
                            map.replace("isVailable", false, true);
                        }
                    }
                }
            }

            returnMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
            returnMap.put("movies", new Gson().fromJson(new Gson().toJson(movieList), movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
        }

        /* 영화, 날짜가 선택되었을 때 */
        if(movieId != null && cinemaId == null && date != null){
            /* Moives */
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(MovieMapping movie : movieOrderList){
                movieList.add(new MovieDto(movie));
            }

            for(Long id : timeTableRepository.findMovieIdByDate(date)){
                    for(MovieDto movieDto : movieList){
                        if(movieDto.getId() == id){
                            movieDto.setIsVailable(true);
                        }
                    }
            }

            /* Dates */
            List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
            for(Long dates : timeTableRepository.findDate()){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", dates);
                map.put("isVailable", false);
                datesList.add(map);
            }
            
            for(Long dates : timeTableRepository.findDateByMovieId(movieId)){
                for(Map<String, Object> map : datesList){
                    if((long)map.get("date") == dates){
                        map.replace("isVailable", false, true);
                    }
                }
            }

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
            for(String cinemaArea : cinemaRepository.findCinemaArea()){
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea)){
                        CinemaDto cinemaDto = new CinemaDto(cinema);
                        for(Long cineId : list){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea) && (cinemaDto.getId() == cineId)){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        cinemaList.add(cinemaDto);
                }
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                cinemasMap.put("cinemaArea", cinemaArea);
                cinemasMap.put("cinemaList",  cinemaList);
                cinemasList.add(cinemasMap);
            }

            returnMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
            returnMap.put("movies", new Gson().fromJson(new Gson().toJson(movieList), movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
        }

        /* 극장, 날짜가 선택되었을 때 */
        if(movieId == null && cinemaId != null && date != null){

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
            for(MovieMapping movie : movieOrderList){
                movieList.add(new MovieDto(movie));
            }

            for(Long id : movieIdList){
                for(MovieDto movieDto : movieList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }                
            }

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
            for(String cinemaArea : cinemaRepository.findCinemaArea()){
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea)){
                        CinemaDto cinemaDto = new CinemaDto(cinema);
                        for(Long cineId : list){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea) && (cinemaDto.getId() == cineId)){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        cinemaList.add(cinemaDto);
                }
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                cinemasMap.put("cinemaArea", cinemaArea);
                cinemasMap.put("cinemaList", cinemaList);
                cinemasList.add(cinemasMap);
            }

            /* Dates */
            List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
            for(Long dates : timeTableRepository.findDate()){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", dates);
                map.put("isVailable", false);
                datesList.add(map);
            }
            
            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenId(id)){
                    for(Map<String, Object> map : datesList){
                        if((long)map.get("date") == dates){
                            map.replace("isVailable", false, true);
                        }
                    }
                }
            }

            returnMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
            returnMap.put("movies", new Gson().fromJson(new Gson().toJson(movieList), movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
        }
        
        /* 영화, 극장, 날짜가 선택되었을 때 */
        if(movieId != null && cinemaId != null && date != null){
            /* Moives */
            List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);

            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(MovieMapping movie : movieOrderList){
                movieList.add(new MovieDto(movie));
            }

            for(MovieDto movieDto : movieList){
                if(movieDto.getId() == movieId){
                    movieDto.setIsVailable(true);
                }
            }
            
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
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                for(Cinema cinema : cinemaRepository.findAllByCinemaArea(cinemaArea.toString())){
                    //for(CinemaDto cinemaDto : cinemaList){
                        CinemaDto cinemaDto = new CinemaDto(cinema);
                        for(Long cineId : list){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && (cinemaDto.getId() == cineId)){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        cinemaList.add(cinemaDto);
                }
                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", cinemaList);
                cinemasList.add(cinemasMap);
            }

            /* Dates */
            List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
            for(Long dates : timeTableRepository.findDate()){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("date", dates);
                map.put("isVailable", false);
                datesList.add(map);
            }
            
            for(Long id : screenIdList){
                for(Long dates : timeTableRepository.findDateByScreenIdAndMovieId(id, movieId)){
                    // if(!datesList.contains(dates)){
                    //     datesList.add(dates);
                    // }
                    for(Map<String, Object> map : datesList){
                        if((long)map.get("date") == dates){
                            map.replace("isVailable", false, true);
                        }
                    }
                }
            }

            /* TimeTables */                                                      
            /* 극장별 날짜,영화에 해당되는 상영 시간표 */
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

            returnMap.put("cinemas", new Gson().fromJson(new Gson().toJson(cinemasList), cinemasList.getClass()));
            returnMap.put("movies", new Gson().fromJson(new Gson().toJson(movieList), movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(new Gson().toJson(datesList), datesList.getClass()));
            returnMap.put("showtimes", new Gson().fromJson(new Gson().toJson(screensList), screensList.getClass()));
        }

        return returnMap;
    }//end of getScreensInfoMap

    public Map<String, Object> getSeatsMap(Long timeTableId) throws Exception {

        List<Seat> seatsList = seatRepository.findByScreenId(
                ((Ticket)((List)ticketRepository.findAllByTimeTableId(timeTableId)).get(0)).getScreenId());
        List<String> seatRowList = new ArrayList<String>();
        for(Seat seat : seatsList){
            String seatRow = seat.getSeatNo().substring(0,1);
            if(!seatRowList.contains(seatRow)){
                seatRowList.add(seatRow);
            }
        }
        
        Map<String, Object> tempMap = new HashMap<String, Object>();
        for(String seatRow : seatRowList){
            
            List<Long> seatIdList = new ArrayList<Long>();
            for(Seat seat : seatsList){
                String seatRows = seat.getSeatNo().substring(0,1);
                if(seatRows.equals(seatRow)){
                    seatIdList.add(seat.getId());
                }
            }
            tempMap.put(seatRow, seatIdList);
        }
        
        List<Object> tempList = new ArrayList<Object>();
        List<Ticket> ticketsList = ticketRepository.findAllByTimeTableId(timeTableId);
        for(String seatRow : seatRowList){
            List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
            for(Object seatId : (List)tempMap.get(seatRow)){ 
                for(Ticket ticket : ticketsList){
                    if(seatId == ticket.getSeatId()){                                
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ticket", ticket);
                        map.put("seat", seatRepository.findOneById(ticket.getSeatId()));
                        lists.add(map);
                    }
                }
            }
            tempMap.replace(seatRow, (List)tempMap.get(seatRow), lists);
            tempList.add(tempMap.get(seatRow));
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("seats", new Gson().fromJson(new Gson().toJson(tempList), tempList.getClass()));

        return returnMap;
    }//end of getSeatsMap

    /* Ticket 상태값 변경 :: 예매 결제 ~ 결제 취소에서 사용 */
    public Map<String, Object> updateTicketState(Map<String, Object> tickets, HttpSession session) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        char ticketState = ((String)tickets.get("state")).charAt(0);
        boolean transaction = false;
        String ticketToken = null;
        List ticketsList = null;

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
        if(ticketState == '0'){    //  예매진행
            if((List)tickets.get("tickets") != null){    //  ticketId Null Check
                for(Object ticketId : (List)tickets.get("tickets")){
                    if(ticketRepository.findOneById((long)((int)ticketId)).getTicketState() == '1'){
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
        }else if(ticketState == '1'){    //  예매취소
            if(tickets.get("ticketTokens") != null){    //  토큰 유효성 검사
                ticketsList = new ArrayList();
                for(Object token : (List)tickets.get("ticketTokens")){
                    List list = ticketRepository.findIdByTicketToken(String.valueOf(token));
                    if(list.size() != 0){    //  ticketId 유효성 체크
                        ticketsList.add(list.get(0));
                    }else{    //  ticketId 값이 없을 경우
                        break;
                    }
                }
                if(ticketsList.size() > 0){    //  tickets List Null Check
                    tickets.put("tickets", ticketsList);
                    transaction = true;
                }else{    //  tickets List 값이 없을 경우
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
                if(ticketState == '0'){    //  예매진행 시 토큰 INSERT
                    ticketToken = (new Random().nextInt(99999)+100000)+""+System.currentTimeMillis();
                    ticketTokens.add(ticketToken);
                    ticket.setTicketToken(ticketToken);
                    ticket.setAccountId(((Account)session.getAttribute("account")).getAccountId());
                }else if(ticketState == '1'){    //  예매취소 시 토큰 DELETE
                    ticket.setTicketToken(null);
                    ticket.setAccountId(null);
                }
                ticketRepository.save(ticket);
            }
            returnMap.put("result", "1");
            returnMap.put("ticketTokens", ticketTokens);    //  결제 취소를 진행할 때, ticketId 대신에 ticketToken 사용
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

                            ticketHisotryRepository.save(
                                TicketHistory.builder().seatId((long)ticket.getSeatId())
                                                        .screenId((long)ticket.getScreenId())
                                                        .movieId((long)ticket.getMovieId())
                                                        .ticketPrice(ticket.getTicketPrice())
                                                        .accountId(ticket.getAccountId()).build());
                            ticketRepository.delete(ticket);
                        }
                    }
                    tickets = tickets.substring(0, tickets.length()-1);
                }

                if(everythingsOk){    //  기프트카드, 포인트 잔액 유효성 검사에 성공했을 경우
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
                    returnMap.put("result", "0");    //  기프트카드, 포인트 잔액조회 검사에서 실패했을 경우
                }
                //end of getPurchase
            }else{
                returnMap.put("result", "0");    //  IamPort Server에 해당 Data가 없을 경우
            }//end of token Validation Check
        }else{
            returnMap.put("result", "0");    //  imp_uid가 Map에 없을 경우
        }//end of imp_uid Validation Check
    
        return returnMap;
    }//end of addPurchase

}//end of class
