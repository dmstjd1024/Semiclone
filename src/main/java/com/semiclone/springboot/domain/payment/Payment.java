package com.semiclone.springboot.domain.payment;

import java.util.Date;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  결제 고유번호
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date purchaseDate = new Date(System.currentTimeMillis());    //  결제 날짜

    @Column(nullable = false)
    private String receiverName;    //  수취인 이름

    @Column(nullable = false)
    private String receiverPhone;    //  수취인 전화번호

    @Column(nullable = false)
    private int paymentAmount;    //  총 결제금액

    @Column(nullable = false)
    private String accountId;    //  사용자 아이디

    @Column(nullable = false)
    private String paymentMethod;    //  결제 방식

    @Column(nullable = false)
    private String paymentStatus;    //  결제 상태

    private String gitfCardIds;    //  기프트카드 고유번호
    private String movieCouponIds;    //  영화관람권 고유번호
    private String ticketIds;    //  티켓 고유번호

    @Builder
    public Payment(String receiverName, String receiverPhone, int paymentAmount, 
                    String accountId, String paymentMethod, String paymentStatus, 
                    String giftCardIds, String movieCouponIds, String ticketIds) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.paymentAmount = paymentAmount;
        this.accountId = accountId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.gitfCardIds = giftCardIds;
        this.movieCouponIds = movieCouponIds;
        this.ticketIds = ticketIds;
    }

}//end of class