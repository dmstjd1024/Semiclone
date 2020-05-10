package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.ticket.TicketMapping;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TicketBySeatDto {

    private Long id;    //  티켓 고유번호
    private Long seatId;    //  좌석 고유번호
    private int ticketPrice;    //  티켓 가격
    private char ticketState;    //  티켓 진행상태

    public TicketBySeatDto(TicketMapping ticket){
        this.id = ticket.getId();
        this.seatId = ticket.getSeatId();
        this.ticketPrice = ticket.getTicketPrice();
        this.ticketState = ticket.getTicketState();
    }

}