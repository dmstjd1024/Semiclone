package com.semiclone.springboot.service.ticket;

import java.util.Map;

public interface TicketService {

    public Map<String, Object> getScreensMap() throws Exception;

    public Map<String, Object> getScreensInfoMap(Long movieId, Long cinemaId, Long date, Long timeTableId, String group) throws Exception;

    public Map<String, Object> getSeatsMap(Long timeTableId) throws Exception;

    public Map<String, Object> updateTicketState(Map<String, Object> tickets) throws Exception;

}//end of interface