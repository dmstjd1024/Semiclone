package com.semiclone.springboot.domain.payment;

import java.sql.Date;

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
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;    // 결제 고유번호

    @Column(nullable = false)
    private Date purchase_date;    //  결제 날짜

    @Column(nullable = false)
    private String receiver_name;    //  수취인 이름

    @Column(nullable = false)
    private String receiver_phone;    //  수취인 전화번호

    @Column(nullable = false)
    private int payment_amount;    //  총 결제가격

    @Column(nullable = false)
    private long user;    //  사용자

    @Column(nullable = false)
    private String payment_method;    //  결제방식

    @Column(nullable = false)
    private String payment_status;    //  결제상태

    @Column(nullable = false)
    private String depositor_name;    //  예금자 성명

    @Column(nullable = false)
    private Date deposite_date;    //  예금 날짜

    @Column(nullable = false)
    private String deposite_bank;    //  예금 은행

    @Column(nullable = false)
    private long ticket;    //  결제 티켓

    private long sale_coupon;    //  사용한 쿠폰
    private long gitfcard;    //  사용 기프트콘
    private long movie_coupon;    //  영화 쿠폰
    private long ticketId;    //  티켓 고유번호

}//end of class