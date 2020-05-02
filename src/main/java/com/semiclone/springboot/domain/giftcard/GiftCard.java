package com.semiclone.springboot.domain.giftcard;

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
public class GiftCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // 기프트카드 고유번호

    @Column(nullable = false)
    private String giftCardName;    //  기프트카드 이름

    private int giftCardBalance;    //  기프트카드 잔액

    @Column(nullable = false)
    private long giftCardEndDate;

    @Column(length = 1, nullable = false)
    private char giftCardState;

    @Column(nullable = false)
    private long accountId;

}//end of class