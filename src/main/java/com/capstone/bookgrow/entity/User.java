package com.capstone.bookgrow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "register_id")
    private String registerId;
    private String password;
    private String name;
    private String nickname;

    // 누적 독서 시간 (시간, 분, 초 단위)
    private int cumulativeHours;
    private int cumulativeMinutes;
    private int cumulativeSeconds;

    private int owned;
    private int complete;
    private int flower;
    private int domination;
}