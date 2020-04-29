package com.semiclone.springboot.domain.tickethistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;    // 영화기록 고유번호

    @Column(nullable = false)
    private long seatId;    //  좌석 고유번호

    @Column(nullable = false)
    private long screenId;    //  상영관 고유번호

    @Column(nullable = false)
    private long movieId;    //  영화 고유번호

    @Column(nullable = false)
    private int ticketPrice;    //  티켓 가격

}//end of class