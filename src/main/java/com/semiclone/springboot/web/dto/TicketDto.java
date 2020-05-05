package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.ticket.Ticket;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TicketDto {

    private Long id;    //  티켓 고유번호
    private Long seatId;    //  좌석 고유번호
    private Long screenId;    //  상영관 고유번호
    private Long movieId;    //  영화 고유번호
    private Long timeTableId;    //  시간표 고유번호
    private int ticketPrice;    //  티켓 가격
    private char ticketState;    //  티켓 진행상태

    public TicketDto(Ticket ticket){
        this.id = ticket.getId();
        this.seatId = ticket.getSeatId();
        this.screenId = ticket.getScreenId();
        this.movieId = ticket.getMovieId();
        this.timeTableId = ticket.getTimeTableId();
        this.ticketPrice = ticket.getTicketPrice();
        this.ticketState = ticket.getTicketState();
    }

}//end of class