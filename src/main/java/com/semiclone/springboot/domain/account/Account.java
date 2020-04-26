package com.semiclone.springboot.domain.account;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountId;
    @Column(nullable = false)
    private String name;
    private String profileImage;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;

    private String nickname;
    private Long ticket;
    private Long breakdowns;
    private Long gifticons;
    private Long coupons;
    @Column(nullable = false)
    private int point;
    private Long paymentDetail;
    private Long watchedMovieList;
    private Long frequentTheater;
    private String userRole;

    @Builder
    public Account(String accountId, String name, String password,
                   String email, String phoneNumber, String nickname, int point, String userRole) {
        this.accountId = accountId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.point = point;
        this.userRole = userRole;
    }
}


