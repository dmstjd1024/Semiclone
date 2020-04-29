package com.semiclone.springboot.domain.giftcard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;

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
    private Long Id;    // 기프트카드 고유번호

    @Column(nullable = false)
    private int giftCardPrice;    //  기프트카드 가격

    @Column(nullable = false)
    private int giftCardBalance;    //  기프트카드 잔액

    @Column(nullable = false)
    private long giftCardStartDate;    //  기프트카드 발급날짜

    @Column(nullable = false)
    @ColumnDefault("1")
    private char giftCardState = 1;    //  기프트카드 상태

}//end of class