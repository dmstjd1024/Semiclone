package com.semiclone.springboot.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieCouponRepository extends JpaRepository<MovieCoupon,Long> {

    List<MovieCoupon> findByAccountId(String accountId);



}
