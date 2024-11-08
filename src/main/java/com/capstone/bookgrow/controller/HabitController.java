package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.Habit;
import com.capstone.bookgrow.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bookgrow/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    // 특정 사용자의 일별 평균 독서 쪽 수와 쪽별 걸린 시간을 반환
    @GetMapping("/get")
    public ResponseEntity<Map<String, Double>> getHabitStats(@RequestParam Long userId) {
        // 독서 페이지 수를 최신 상태로 계산하여 Habit에 업데이트
        habitService.calculateTotalReadPagesAndUpdateHabit(userId);

        Habit habit = habitService.getHabitByUserId(userId);

        double averagePagesPerDay = habitService.calculateAveragePagesPerDay(habit);
        double timePerPage = habitService.calculateTimePerPage(habit);

        Map<String, Double> stats = new HashMap<>();
        stats.put("averagePagesPerDay", averagePagesPerDay);
        stats.put("timePerPage", timePerPage);

        return ResponseEntity.ok(stats);
    }
}
