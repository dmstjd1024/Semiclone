package com.semiclone.springboot.domain.tickethistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;    // 영화기록 고유번호

    @Column(nullable = false)
    private Long seatId;    //  좌석 고유번호

    @Column(nullable = false)
    private Long screenId;    //  상영관 고유번호

    @Column(nullable = false)
    private Long movieId;    //  영화 고유번호

    @Column(nullable = false)
    private int ticketPrice;    //  티켓 가격

    @Column(nullable = false)
    private String accountId;    //  사용자 아이디

    @Builder
    public TicketHistory(Long seatId, Long screenId, Long movieId, int ticketPrice, String accountId){
        this.seatId = seatId;
        this.screenId = screenId;
        this.movieId = movieId;
        this.ticketPrice = ticketPrice;
        this.accountId = accountId;
    }

}//end of class