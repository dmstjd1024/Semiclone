package com.semiclone.springboot.service.timetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.Movie;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.timetable.TimeTable;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.service.ticket.TicketService;
import com.semiclone.springboot.web.dto.MovieDetailDto;
import com.semiclone.springboot.web.dto.MovieInfoDto;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service("showTimeServiceImpl")
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {

    //Field
    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final TimeTableRepository timeTableRepository;
    private final TicketService ticketService;

    /* 극장 리스트 */
    public Map<String, Object> getCinemas() throws Exception {
        
        List<Map<String, Object>> cinemasList = ticketService.getCinemasList(null);    // Cinemas

        return ticketService.getReturnJsonMap(null, cinemasList, null, null, null);

    }//end of getCinemas
    
    /* 영화 리스트 */
    public Map<String, Object> getMovies() throws Exception {
        
        List<MovieDetailDto> movieList = new ArrayList<MovieDetailDto>();
        for(Movie obj : movieRepository.findAll()){
            movieList.add(new MovieDetailDto(obj));
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("movies", new Gson().fromJson(new Gson().toJson(movieList), movieList.getClass()));

        return returnMap;
    }//end of getMovies

    /* 극장별 상영시간표 */
    public Map<String, Object> getTimeTablesByCinemaId(Long cinemaId, Long date) throws Exception {
        
        date = (date == 123890) ? null : date;    // 테스트용 null값

        List<Long> screenIdsList = screenRepository.findIdByCinemaId(cinemaId);
        date = (date == null || date == 0) ? timeTableRepository.findFirstByCinemaIdOrderByDate(cinemaId).getDate() : date ;
                                                      
        /* 극장별 날짜,영화에 해당되는 상영 시간표 */
        List<Object> timeTablesList = new ArrayList<Object>();
        for(Long movieId : timeTableRepository.findMovieIdByCinemaIdAndDate(cinemaId, date)){
            List<Map<String, Object>> screensList = new ArrayList<Map<String, Object>>();
            for(Long screenId : screenIdsList){
                List<TimeTable> list = timeTableRepository.findTimeTableByMovieIdAndScreenIdAndDate(movieId, screenId, date);
                Map<String, Object> screensMap = new HashMap<String, Object>();
                if(list.size() != 0){
                    screensMap.put("screen", screenRepository.findOneById(screenId));
                    screensMap.put("timeTables", list);
                    screensList.add(screensMap);
                }
            }
            Map<String, Object> moviesMap = new HashMap<String, Object>();
            moviesMap.put("movie", new MovieInfoDto(movieRepository.findOneById(movieId)));
            moviesMap.put("screens", screensList);
            timeTablesList.add(moviesMap);
        }
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("showtimes", new Gson().fromJson(new Gson().toJson(timeTablesList), timeTablesList.getClass()));

        return returnMap;
    }//end of getTimeTablesByCinemaId

    /* 영화별 상영시간표 */
    public Map<String, Object> getTimeTablesByMovieId(Long movieId, String cinemaArea, Long date) throws Exception {
        
        cinemaArea = cinemaArea.equals("123890") ? "" : cinemaArea;    // Test용 null
        date = (date == 123890) ? null : date;    // Test용 null
        
        /* 극장 지역 정보가 없을 경우 1번 레코드로 초기화 */
        if(cinemaArea.equals("") || cinemaArea == null){
            cinemaArea = cinemaRepository.findCinemaAreaById((long)1);
        }

        /* 날짜 정보가 없을 경우 영화, 지역을 포함한 날짜의 1번 레코드로 초기화 */
        if(date == null || date == 0){
            date = timeTableRepository.findDateByCinemaAreaAndMovieId(cinemaArea, movieId).get(0);
        }
        
        /* 영화별 지역, 날짜에 해당되는 상영 시간표 */
        List<Object> timeTablesList = new ArrayList<Object>();
        for(Long cinemaId : cinemaRepository.findIdListByCinemaArea(cinemaArea)){
            
            List<Map<String, Object>> screensList = new ArrayList<Map<String, Object>>();
            for(Long screenId : timeTableRepository.findScreenIdByMovieIdAndDateAndCinemaId(movieId, date, cinemaId)){
                List<TimeTable> list = timeTableRepository.findTimeTableByMovieIdAndScreenIdAndDate(movieId, screenId, date);
                Map<String, Object> screensMap = new HashMap<String, Object>();
                screensMap.put("screen", screenRepository.findOneById(screenId));
                screensMap.put("timeTables", list);
                screensList.add(screensMap);
            }
            Map<String, Object> cinemasMap = new HashMap<String, Object>();
            cinemasMap.put("cinemaName", cinemaRepository.findCinemaNameById(cinemaId));
            cinemasMap.put("screens", screensList);
            timeTablesList.add(cinemasMap);
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("showtimes", new Gson().fromJson(new Gson().toJson(timeTablesList), timeTablesList.getClass()));

        return returnMap;

    }//end of getTimeTablesByMovieId

}//end of class