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
import com.semiclone.springboot.domain.account.Account;
import com.semiclone.springboot.domain.account.AccountRepository;
import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.coupon.MovieCouponRepository;
import com.semiclone.springboot.domain.giftcard.GiftCardRepository;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.payment.Payment;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.seat.SeatRepository;
import com.semiclone.springboot.domain.ticket.Ticket;
import com.semiclone.springboot.domain.ticket.TicketRepository;
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

    //Method
    /* 모든 영화, 극장, 날짜 리스트 return */
    public Map<String, Object> getScreensMap() throws Exception{        
        Map<String, Object> returnMap = new HashMap<String, Object>();

        /* Moives */
        List<MovieDto> movieList = new ArrayList<MovieDto>();
        for(Movie obj : movieRepository.findAll()){
            MovieDto movieDto = new MovieDto(obj);
            movieDto.setIsVailable(true);
            movieList.add(movieDto);
        }
        String moviesJson = new Gson().toJson(movieList);

        /* Cinemas */
        List<Object> cinemasList = new ArrayList<Object>();
        for(Object cinemaArea : cinemaRepository.findCinemaArea()){

            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            Map<String, Object> cinemasMap = new HashMap<String, Object>();
            for(Object obj : cinemaRepository.findAllByCinemaArea(cinemaArea.toString())){
                CinemaDto cinemaDto = new CinemaDto((Cinema)obj);
                cinemaDto.setIsVailable(true);
                cinemaList.add(cinemaDto);
            }
            String cinemasJson = new Gson().toJson(cinemaList);

            cinemasMap.put("cinemaArea", cinemaArea.toString());
            cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
            cinemasList.add(cinemasMap);
        }

        /* Dates */
        List<Map<String, Object>> datesList = new ArrayList<Map<String, Object>>();
        for(Long dates : timeTableRepository.findDate()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("date", dates);
            map.put("isVailable", true);
            datesList.add(map);
        }
        String datesJson = new Gson().toJson(datesList);

        returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
        returnMap.put("cinemas", cinemasList);
        returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
       
        return returnMap;
    }//end of getScreensMap

    public Map<String, Object> getScreensInfoMap(Long movieId, Long cinemaId, Long date, String group) throws Exception{
        
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
        if(group.equals("123890")){
            group = "0";
        }

        /* 모든 Param이 null일 때 전체값 return */
        if(movieId == null && cinemaId == null && date == null && group.equals("")){
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
        if(movieId != null && cinemaId == null && date == null){

            /* Cinemas */
            List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByMovieId(movieId)){
                Long id = screenRepository.findCinemaIdById(obj);
                if(!list.contains(id)){
                    list.add(id);
                }
            }
            list.sort(null);    //  리스트 정렬

            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAll()){
                cinemaList.add(new CinemaDto(cinema));
            }

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                //List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                for(Long cineId : list){
                    //for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                    for(CinemaDto cinemaDto : cinemaList){
                        //cinemaList.add(new CinemaDto((Cinema)obj));
                        if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && (cinemaDto.getId() == cineId)){
                            cinemaDto.setIsVailable(true);
                        }
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
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

            for(Long dates : timeTableRepository.findDateByMovieId(movieId)){
                for(Map<String, Object> map : datesList){
                    if((long)map.get("date") == dates){
                        map.replace("isVailable", false, true);
                    }
                }
            }
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
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
            
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Movie movie : movieRepository.findAll()){
                movieList.add(new MovieDto(movie));
            }

            for(Long id : movieIdList){
                for(MovieDto movieDto : movieList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }
                //movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

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
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }

        /* 날짜만 선택했을 때 */
        if(movieId == null && cinemaId == null && date != null){
            /* Moives */
            List<MovieDto> moviesList = new ArrayList<MovieDto>();
            for(Movie movie : movieRepository.findAll()){
                moviesList.add(new MovieDto(movie));
            }

            for(Long id : timeTableRepository.findMovieIdByDate(date)){
                for(MovieDto movieDto : moviesList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }
                //moviesList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(moviesList);

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
            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAll()){
                cinemaList.add(new CinemaDto(cinema));
            }

            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                //List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    //for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        for(CinemaDto cinemaDto : cinemaList){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && cinemaDto.getId() == cineId){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        //cinemaList.add(new CinemaDto((Cinema)obj));
                    //}
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
                cinemasList.add(cinemasMap);
            }

            returnMap.put("movies", new Gson().fromJson(moviesJson, moviesList.getClass()));
            returnMap.put("cinemas", cinemasList);
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
            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAll()){
                cinemaList.add(new CinemaDto(cinema));
            }

            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    //for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        for(CinemaDto cinemaDto : cinemaList){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && cinemaDto.getId() == cineId){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        //cinemaList.add(new CinemaDto((Cinema)obj));
                    //}
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
            for(Movie movie : movieRepository.findAll()){
                movieList.add(new MovieDto(movie));
            }

            for(Long id : movieIdList){
                for(MovieDto movieDto : movieList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }
                //movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

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
                    // if(!datesList.contains(dates)){
                    //     datesList.add(dates);
                    // }
                }
            }
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }

        /* 영화, 날짜가 선택되었을 때 */
        if(movieId != null && cinemaId == null && date != null){
            /* Moives */
            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Movie movie : movieRepository.findAll()){
                movieList.add(new MovieDto(movie));
            }

            for(Long id : timeTableRepository.findMovieIdByDate(date)){
                    for(MovieDto movieDto : movieList){
                        if(movieDto.getId() == id){
                            movieDto.setIsVailable(true);
                        }
                    }
                    //movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            }
            String moviesJson = new Gson().toJson(movieList);

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

            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAll()){
                cinemaList.add(new CinemaDto(cinema));
            }

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    //for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        for(CinemaDto cinemaDto : cinemaList){
                            if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && cinemaDto.getId() == cineId){
                                cinemaDto.setIsVailable(true);
                            }
                        }
                        //cinemaList.add(new CinemaDto((Cinema)obj));
                    //}
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
            for(Movie movie : movieRepository.findAll()){
                movieList.add(new MovieDto(movie));
            }

            for(Long id : movieIdList){
                for(MovieDto movieDto : movieList){
                    if(movieDto.getId() == id){
                        movieDto.setIsVailable(true);
                    }
                }                
                //movieList.add(new MovieDto(movieRepository.findMovieById(id)));
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

            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAll()){
                cinemaList.add(new CinemaDto(cinema));
            }

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    //for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                        //cinemaList.add(new CinemaDto((Cinema)obj));
                    //}
                    for(CinemaDto cinemaDto : cinemaList){
                        if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && cinemaDto.getId() == cineId){
                            cinemaDto.setIsVailable(true);
                        }
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
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
                    // if(!datesList.contains(dates)){
                    //     datesList.add(dates);
                    // }
                }
            }
            String datesJson = new Gson().toJson(datesList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
        }
        
        /* 영화, 극장, 날짜가 선택되었을 때 */
        if(movieId != null && cinemaId != null && date != null){
            /* Moives */
            List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);

            List<MovieDto> movieList = new ArrayList<MovieDto>();
            for(Movie movie : movieRepository.findAll()){
                movieList.add(new MovieDto(movie));
            }

            for(MovieDto movieDto : movieList){
                if(movieDto.getId() == movieId){
                    movieDto.setIsVailable(true);
                }
            }
                //movieList.add(new MovieDto(movieRepository.findMovieById(id)));
            
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

            List<CinemaDto> cinemaList = new ArrayList<CinemaDto>();
            for(Cinema cinema : cinemaRepository.findAll()){
                cinemaList.add(new CinemaDto(cinema));
            }

            List<Object> cinemasList = new ArrayList<Object>();
            for(Object cinemaArea : cinemaRepository.findCinemaArea()){
                
                Map<String, Object> cinemasMap = new HashMap<String, Object>();
                for(Long cineId : list){
                    // for(Object obj : cinemaRepository.findAllByCinemaAreaAndId(cinemaArea.toString(),cineId)){
                    //     cinemaList.add(new CinemaDto((Cinema)obj));
                    // }
                    for(CinemaDto cinemaDto : cinemaList){
                        if(cinemaDto.getCinemaArea().equals(cinemaArea.toString()) && cinemaDto.getId() == cineId){
                            cinemaDto.setIsVailable(true);
                        }
                    }
                }
                String cinemasJson = new Gson().toJson(cinemaList);

                cinemasMap.put("cinemaArea", cinemaArea.toString());
                cinemasMap.put("cinemaList", new Gson().fromJson(cinemasJson, cinemaList.getClass()));
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
            String datesJson = new Gson().toJson(datesList);

            /* TimeTables */                                                      
            /* 극장별 날짜,영화에 해당되는 상영 시간표 */
            List<Map<String, Object>> screensList = new ArrayList<Map<String, Object>>();
            for(Long screenId : screenIdList){
                System.out.println("/////////////////"+screenId);
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
            String cinemasJson = new Gson().toJson(screensList);

            ///
            // List<TimeTable> timeTableList = new ArrayList<TimeTable>();
            // for(Long id : screenIdList){
            //     for(TimeTable obj : timeTableRepository.findTimeTableByMovieIdAndScreenIdAndDate(movieId, id, date)){
            //         timeTableList.add(obj);
            //     }
            // }
            // String timeTablesJson = new Gson().toJson(timeTableList);

            returnMap.put("cinemas", cinemasList);
            returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));
            returnMap.put("dates", new Gson().fromJson(datesJson, datesList.getClass()));
            //returnMap.put("timeTables", new Gson().fromJson(timeTablesJson, timeTableList.getClass()));
            returnMap.put("showtimes", new Gson().fromJson(cinemasJson, screensList.getClass()));
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

    public Map<String, Object> addPurchase(Map<String, Object> purchaseMap, HttpSession session) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        if(purchaseMap.containsKey("imp_uid")){
            
            String API_URL = "https://api.iamport.kr";
            String api_key = "";
            String api_secret = "";
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
                IamportResponse<Purchase> purchase = gson.fromJson(responsed, listTypes);
                
                System.out.println(purchase.getResponse().getBuyerTel());

                String movieCoupons = "";
                String tickets = "";
                String giftCards = "";
                if(purchaseMap.containsKey("movieCoupons") && ((List)purchaseMap.get("movieCoupons")).size() > 0){    //  movieCoupons 유효성 검사
                    for(Object movieCoupon : (List)purchaseMap.get("movieCoupons")){
                        movieCoupons += movieCoupon+",";
                    }
                    movieCoupons.substring(0, movieCoupons.length()-1);
                }
                if(purchaseMap.containsKey("tickets") && ((List)purchaseMap.get("tickets")).size() > 0){    //  tickets 유효성 검사
                    for(Object ticket : (List)purchaseMap.get("tickets")){

                    }
                }
                if(purchaseMap.containsKey("giftCards") && ((List)purchaseMap.get("giftCards")).size() > 0){    //  giftCards 유효성 검사
                    for(Object giftCard : (List)purchaseMap.get("giftCards")){

                    }
                }

                Payment.builder().build();

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
