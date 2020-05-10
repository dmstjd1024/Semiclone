package com.semiclone.springboot.service.ticket;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.semiclone.springboot.web.dto.MovieDto;

public interface TicketService {

    public Map<String, Object> getScreensMap() throws Exception;

    public Map<String, Object> getScreensInfoMap(Long movieId, Long cinemaId, Long date, String group) throws Exception;

    public Map<String, Object> getSeatsMap(Long timeTableId) throws Exception;

    public Map<String, Object> updateTicketState(Map<String, Object> tickets, HttpSession session) throws Exception;

    public Map<String, Object> getUserService(String accountId, HttpSession session) throws Exception;

    public Map<String, Object> addPurchase(Map<String, Object> purchase, HttpSession session) throws Exception;

    public List<Map<String, Object>> getCinemasList(List<Long> cinemaIdsList) throws Exception;

    public List<MovieDto> getMoviesList(List<Long> movieIdsList, String group) throws Exception;

    public Map<String, Object> getReturnJsonMap(List<MovieDto> moviesList, List<Map<String, Object>> cinemasList, 
            List<Map<String, Object>> datesList, List<Map<String, Object>> screensList) throws Exception;

    public List<Map<String, Object>> getDatesList(List<Long> selectionList) throws Exception;
    
    public List<Map<String, Object>> getScreensList(List<Long> screenIdList, Long movieId, Long date) throws Exception;
    

            

}//end of interface