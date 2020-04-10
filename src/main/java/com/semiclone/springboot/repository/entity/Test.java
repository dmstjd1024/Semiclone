package com.semiclone.springboot.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Test {

    @Id
    private Long id;

    @Column
    private String context;
}
