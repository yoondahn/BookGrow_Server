package com.capstone.bookgrow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int totalReadPages; // 총 읽은 페이지 수
    private LocalDate startDate; // 독서 습관 기록 시작 날짜

    @Transient
    private double averagePagesPerDay;  // 일별 평균 독서 쪽 수

    @Transient
    private double timePerPage; // 쪽별 걸린 시간
}
