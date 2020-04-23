package com.semiclone.springboot.service.timetable;

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
import com.semiclone.springboot.domain.timetable.TimeTable;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.web.dto.CinemaDto;
import com.semiclone.springboot.web.dto.MovieDto;

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

    /* 극장 리스트 */
    public Map<String, Object> getCinemas() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
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
        returnMap.put("cinemas", cinemasList);

        return returnMap;
    }//end of getCinemas
    
    /* 영화 리스트 */
    public Map<String, Object> getMovies() throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        List<MovieDto> movieList = new ArrayList<MovieDto>();
        for(Movie obj : movieRepository.findAll()){
            movieList.add(new MovieDto(obj));
        }
        String moviesJson = new Gson().toJson(movieList);
        returnMap.put("movies", new Gson().fromJson(moviesJson, movieList.getClass()));

        return returnMap;
    }//end of getMovies

    /* 극장별 상영시간표 */
    public Map<String, Object> getTimeTablesByCinemaId(Long cinemaId, Long date) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<Long> screenIdList = screenRepository.findIdByCinemaId(cinemaId);

        if(date == 123890){    //  테스트용 null값
            date = null;
        }

        if(date == null || date == 0){    //  날짜 정보가 없을 경우 초기화
            List<Long> datesList = new ArrayList<Long>();
            for(Long screenId : screenIdList){
                for(Long dateInfo : timeTableRepository.findDateByScreenId(screenId)){
                    if(!datesList.contains(dateInfo)){
                        datesList.add(dateInfo);
                    }
                }
            }
            datesList.sort(null);
            date = datesList.get(0);
        }
    
        /* 영화별 맵핑을 위한 movieIdList */
        List<Long> movieIdList = new ArrayList<Long>();
        for(Long screenId : screenIdList){
            for(Long movieId : timeTableRepository.findMovieIdByScreenIdAndDate(screenId, date)){
                if(!movieIdList.contains(movieId)){
                    movieIdList.add(movieId);
                }
            }
        }
                                                      
        /* 극장별 날짜,영화에 해당되는 상영 시간표 */
        List<Object> timeTablesList = new ArrayList<Object>();
        for(Long movieId : movieIdList){
            List<Map<String, Object>> screensList = new ArrayList<Map<String, Object>>();
            for(Long screenId : screenIdList){
                List<TimeTable> list = timeTableRepository.findTimeTableByMovieIdAndScreenIdAndDate(movieId, screenId, date);
                if(list.size() != 0){
                    Map<String, Object> screensMap = new HashMap<String, Object>();
                    screensMap.put("screen", screenRepository.findOneById(screenId));
                    screensMap.put("timeTables", list);
                    screensList.add(screensMap);
                }
            }
            Map<String, Object> moviesMap = new HashMap<String, Object>();
            moviesMap.put("movie", movieRepository.findOneById(movieId));
            moviesMap.put("screens", screensList);
            timeTablesList.add(moviesMap);
        }
        String cinemasJson = new Gson().toJson(timeTablesList);
        returnMap.put("showtimes", new Gson().fromJson(cinemasJson, timeTablesList.getClass()));

        return returnMap;
    }//end of getTimeTablesByCinemaId

    /* 영화별 상영시간표 */
    public Map<String, Object> getTimeTablesByMovieId(Long movieId, String cinemaArea, Long date) throws Exception {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        if(cinemaArea.equals("123890")){    //  Test용 null
            cinemaArea = "";
        }
        if(date == 123890){
            date = null;
        }
        
        /* 극장 지역 정보가 없을 경우 1번 레코드로 초기화 */
        if(cinemaArea.equals("") || cinemaArea == null){
            cinemaArea = cinemaRepository.findCinemaAreaById((long)1);
        }

        /* 날짜 정보가 없을 경우 영화, 지역을 포함한 날짜의 1번 레코드로 초기화 */
        List<Long> screenIdList = new ArrayList<Long>();
        for(Long cinemaId : cinemaRepository.findIdsByCinemaArea(cinemaArea)){
            for(Long screenId : screenRepository.findIdByCinemaId(cinemaId)){
                if(!screenIdList.contains(screenId)){
                    screenIdList.add(screenId);
                }
            }
        }
        if(date == null || date == 0){
            List<Long> datesList = new ArrayList<Long>();
            for(Long screenId : screenIdList){
                for(Long dateInfo : timeTableRepository.findDateByScreenIdAndMovieId(screenId, movieId)){
                    if(!datesList.contains(dateInfo)){
                        datesList.add(dateInfo);
                    }
                }
            }
            datesList.sort(null);
            date = datesList.get(0);
        }

        /* 영화별 지역, 날짜에 해당되는 상영 시간표 */
        List<Object> timeTablesList = new ArrayList<Object>();
        for(Long cinemaId : cinemaRepository.findIdListByCinemaArea(cinemaArea)){
            List<Map<String, Object>> screensList = new ArrayList<Map<String, Object>>();
            for(Long screenId : timeTableRepository.findScreenIdByMovieIdAndDate(movieId, date)){
                List<TimeTable> list = timeTableRepository.findTimeTableByMovieIdAndScreenIdAndDate(movieId, screenId, date);
                if(list.size() != 0){
                    Map<String, Object> screensMap = new HashMap<String, Object>();
                    screensMap.put("screen", screenRepository.findOneById(screenId));
                    screensMap.put("timeTables", list);
                    screensList.add(screensMap);
                }
            }
            Map<String, Object> cinemasMap = new HashMap<String, Object>();
            cinemasMap.put("cinemaName", cinemaRepository.findCinemaNameById(cinemaId));
            cinemasMap.put("screens", screensList);
            timeTablesList.add(cinemasMap);
        }
        String moviessJson = new Gson().toJson(timeTablesList);
        returnMap.put("showtimes", new Gson().fromJson(moviessJson, timeTablesList.getClass()));

        return returnMap;
    }//end of getTimeTablesByMovieId

}//end of class