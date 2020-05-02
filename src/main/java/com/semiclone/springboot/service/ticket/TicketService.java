package com.semiclone.springboot.service.ticket;

import java.util.Map;

import javax.servlet.http.HttpSession;

public interface TicketService {

    public Map<String, Object> getScreensMap() throws Exception;

    public Map<String, Object> getScreensInfoMap(Long movieId, Long cinemaId, Long date, Long timeTableId, String group) throws Exception;

    public Map<String, Object> getSeatsMap(Long timeTableId) throws Exception;

    public Map<String, Object> updateTicketState(Map<String, Object> tickets) throws Exception;

    public Map<String, Object> getUserService(String accountId, HttpSession session) throws Exception;

    public Map<String, Object> addPurchase(Map<String, Object> payment, HttpSession session) throws Exception;

}//end of interface