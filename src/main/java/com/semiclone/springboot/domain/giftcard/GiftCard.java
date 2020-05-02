package com.semiclone.springboot.domain.giftcard;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GiftCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 기프트카드 고유번호

    @Column(nullable = false)
    private String giftCardName;    //  기프트카드 이름

    @Column(nullable = false)
    private int giftCardBalance = 0;    //  기프트카드 잔액

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date giftCardEndDate;    //  기프트카드 종료날짜

    @Column(length = 1, nullable = false)
    private char giftCardState;    //  기프트카드 상태

    @Column(nullable = false)
    private String accountId;    //  사용자 아이디

    @Column(nullable = false)
    private String giftCardImage;    //  기프트카드 이미지

}//end of class