package com.semiclone.springboot.domain.gifticon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Gifticon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String validity; // 유효기간

    @Column(nullable = false)
    private String status; // 사용여부

    public Gifticon(Long id, String accountId, String productName, String receiver, String phoneNumber, String validity, String status) {
        this.id = id;
        this.accountId = accountId;
        this.productName = productName;
        this.receiver = receiver;
        this.phoneNumber = phoneNumber;
        this.validity = validity;
        this.status = status;
    }
}
