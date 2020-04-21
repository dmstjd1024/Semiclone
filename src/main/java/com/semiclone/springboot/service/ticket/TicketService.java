package com.semiclone.springboot.service.ticket;

import java.util.Map;

public interface TicketService {

    public Map<String, Object> getSelectMap() throws Exception;

    public Map<String, Object> getSelectMap(Long movieId, Long cinemaId, Long date, Long timeTableId, String group) throws Exception;

}//end of interface