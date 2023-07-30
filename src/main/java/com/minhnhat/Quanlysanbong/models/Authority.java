package com.minhnhat.Quanlysanbong.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Authority() {
    }

    public Authority(Long id, ERole name) {
        this.id = id;
        this.name = name;
    }
}