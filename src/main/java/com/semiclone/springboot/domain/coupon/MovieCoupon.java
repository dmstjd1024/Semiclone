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
    private String accountId;    //  사용자 아이디

    @Column(nullable = false)
    private int movieCouponPrice = 5000;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date movieCouponEndDate;

    @Column(nullable = false)
    private char movieCouponState = 1;

    @Builder
    public MovieCoupon(int id, String accountId, int movieCouponPrice, char movieCouponState) {
        this.id = id;
        this.accountId = accountId;
        this.movieCouponPrice = movieCouponPrice;
        this.movieCouponState = movieCouponState;

        Date date = new Date(System.currentTimeMillis());
        date.setYear(date.getYear()+2);
        this.movieCouponEndDate = date;
    }

}



