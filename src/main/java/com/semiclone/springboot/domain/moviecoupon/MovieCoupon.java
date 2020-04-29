package com.semiclone.springboot.domain.moviecoupon;

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
public class MovieCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;    // 영화관람권 고유번호

    @Column(length = 20, nullable = false)
    private String screenState;

    @Column(nullable = false)
    private int movieCouponPrice;

    @Column(nullable = false)
    private long movieCouponStartDate;
    
    @Column(nullable = false)
    private long movieCouponEndDate;

    @Column(nullable = false)
    @ColumnDefault("1")
    private char movieCouponState = 1;

}//end of class