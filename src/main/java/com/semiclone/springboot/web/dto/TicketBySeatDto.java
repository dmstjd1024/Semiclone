package com.semiclone.springboot.web.dto;

import com.semiclone.springboot.domain.ticket.TicketMapping;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TicketBySeatDto {

    private Long ticketId;    //  티켓 고유번호
    private int ticketPrice;    //  티켓 가격
    private char ticketState;    //  티켓 진행상태
    private String seatNo;    // 좌석 번호
    private String seatName;    // 좌석 이름

    public TicketBySeatDto(TicketMapping ticket){
        this.ticketId = ticket.getTicketId();
        this.ticketPrice = ticket.getTicketPrice();
        this.ticketState = ticket.getTicketState();
        this.seatNo = ticket.getSeatNo();
        this.seatName = ticket.getSeatName();
    }

}//end of class