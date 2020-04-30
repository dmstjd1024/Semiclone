package com.semiclone.springboot.domain.coupon;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MovieCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //영화관람권 고유번호

    @Column(nullable = false)
    private String screenState;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private int movieCouponPrice;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date movieCouponStartDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date movieCouponEndDate;

    @Column(nullable = false)
    private char movieCouponState;

    @Builder
    public MovieCoupon(int id, String screenState, String accountId, int movieCouponPrice, Date movieCouponStartDate, Date movieCouponEndDate, char movieCouponState) {
        this.id = id;
        this.screenState = screenState;
        this.accountId = accountId;
        this.movieCouponPrice = movieCouponPrice;
        this.movieCouponStartDate = movieCouponStartDate;
        this.movieCouponEndDate = movieCouponEndDate;
        this.movieCouponState = movieCouponState;
    }

}



