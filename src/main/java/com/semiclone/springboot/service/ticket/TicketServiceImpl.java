package com.semiclone.springboot.service.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
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

        Long screenId = timeTableRepository.findOneById(timeTableId).getScreenId();
        

        return null;
    }

}//end of class
