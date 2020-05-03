package com.semiclone.springboot.domain.giftcard;

import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
@DynamicUpdate
public interface GiftCardRepository extends JpaRepository<GiftCard, Long>{

    @Query("SELECT g FROM GiftCard g WHERE account_id = ?1")
    List<GiftCard> findAllByAccountId(String accountId);

    @Query("SELECT g FROM GiftCard g WHERE id = ?1")
    GiftCard findOneById(Long id);

}//end of interface