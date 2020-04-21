package com.semiclone.springboot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.semiclone.springboot.domain.cinema.Cinema;
import com.semiclone.springboot.domain.cinema.CinemaRepository;
import com.semiclone.springboot.domain.movie.MovieRepository;
import com.semiclone.springboot.domain.screen.ScreenRepository;
import com.semiclone.springboot.domain.timetable.TimeTableRepository;
import com.semiclone.springboot.web.dto.CinemaDto;
import com.semiclone.springboot.web.dto.MovieDto;

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

    //@Test
    public void joinQueryTest() throws Exception {
        
        /* movieId로 cinemaId 얻는 로직 */
        List<Long> list = new ArrayList<Long>();
        for(Long obj : timeTableRepository.findScreenIdByMovieId((long)1)){
            Long cinemaId = screenRepository.findCinemaIdById(obj);
            if(!list.contains(cinemaId)){
                list.add(cinemaId);
            }
        }
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
        System.out.println(datesJson);
    }

    //@Test
    public void dateOnlyTest(){
        /* Moives */
        List<MovieDto> movieList = new ArrayList<MovieDto>();
        for(Long id : timeTableRepository.findMovieIdByDate((long)20200422)){
                movieList.add(new MovieDto(movieRepository.findMovieById(id)));
        }
        String moviesJson = new Gson().toJson(movieList);

        List<Long> list = new ArrayList<Long>();
            for(Long obj : timeTableRepository.findScreenIdByDate((long)20200422)){
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
            for(Object obj : cinemasList){
                System.out.println(obj);
            }
    }

    @Test
    public void movieAndScreenSelect() throws Exception{

        Long movieId = (long)58;
        Long date = (long)20200423;
        Long cinemaId = (long)29;

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

        for(Object obj : cinemasList){
            System.out.println(obj);
        }
        System.out.println(moviesJson);
        System.out.println(datesJson);
        
    }

}