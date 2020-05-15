package com.semiclone.springboot.domain.ticket;

public interface TicketMapping {

    Long getTicketId();
    int getTicketPrice();
    char getTicketState();
    String getSeatNo();
    String getSeatName();

}