package com.capstone.bookgrow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    private Long userId;

    private String title;
    private String author;
    private String publisher;
    private String published_year;
    private String isbn;

    private int total_page;
    private int current_page;
    private String image_url;
    private Boolean is_completed;
}
