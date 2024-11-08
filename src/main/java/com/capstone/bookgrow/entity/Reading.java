package com.capstone.bookgrow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "book_id", unique = true)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    private int start_page;
    private int end_page;
    private String time;

    @ElementCollection
    @CollectionTable(name = "reading_review", joinColumns = @JoinColumn(name = "reading_id"))
    @Column(name = "review")
    private List<String> review = new ArrayList<>();

    private Boolean isCompleted;
}
