package com.semiclone.springboot.domain.payment;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //  결제 고유번호

    @Column(nullable = false)
    private Date purchaseDate;    //  결제 날짜

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

    @Column(nullable = false)
    private String depositorName;    //  예금자 성명

    @Column(nullable = false)
    private String depositeBank;    //  예금 은행

    private long gitfcardId;    //  기프트카드 고유번호
    private long movieCouponId;    //  영화관람권 고유번호
    private long ticketId;    //  티켓 고유번호

}//end of class