package com.semiclone.springboot.domain.ticket;

public interface TicketMapping {

    Long getId();
    int getTicketPrice();
    char getTicketState();
    Long getSeatId();

}