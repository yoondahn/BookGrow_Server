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

    private String title;
    private String author;
    private String publisher;
    private String published_year;
    private String isbn;
    private String total_page;
    private String format;
    private String image_url;
    private String genre;
    private Boolean is_completed;
}
