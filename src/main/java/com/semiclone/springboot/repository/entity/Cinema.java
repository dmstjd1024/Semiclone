package com.semiclone.springboot.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cinema")
@Getter
@ToString
@NoArgsConstructor
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String cinemaArea;

    @Column(length = 20, nullable = false)
    private String cinemaName;

    //Constructor
    public Cinema(String cinemaArea, String cinemaName){
        this.cinemaArea = cinemaArea;
        this.cinemaName = cinemaName;
    }

}//end of Class