package com.semiclone.springboot.service.ticket;

import java.util.Map;

public interface TicketService {

    public Map<String, String> getSelectMap() throws Exception;

    public Map<String, String> getSelectMap(Long movieId, String dimension, Long cinemaId, Long date, String group) throws Exception;

}//end of interface