package com.semiclone.springboot.domain.coupon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieCouponRepository extends JpaRepository<MovieCoupon,Long> {

    @Query("SELECT m FROM MovieCoupon m WHERE accountId = ?1 AND movieCouponState = 1")
    List<MovieCoupon> findByAccountId(String accountId);

    @Query("SELECT m FROM MovieCoupon m WHERE id = ?1")
    MovieCoupon findOneById(Long id);

}
