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

    private String pages_per_day;
    private String time_per_page;
    private int owned;
    private int complete;
    private int flower;
    private int domination;
}