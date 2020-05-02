package com.semiclone.springboot.domain.giftcard;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long>{

    @Query("SELECT g FROM GiftCard g WHERE account_id = ?1")
    List<GiftCard> findAllByAccountId(String accountId);

}//end of interface