package com.capstone.bookgrow.repository;

import com.capstone.bookgrow.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    Habit findByUserId(Long userId);
}
