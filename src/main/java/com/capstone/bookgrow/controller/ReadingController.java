package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.service.ReadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookgrow/reading")
@Slf4j
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    // Reading 추가 또는 업데이트
    @PostMapping("/add")
    public ResponseEntity<Reading> addOrUpdateReading(@RequestBody Reading reading, @RequestParam Long bookId,
                                                      @RequestParam Long userId) {
        log.info("Reading 추가 또는 업데이트 요청: bookId={}, userId={}, review={}, isCompleted={}",
                bookId, userId, reading.getReview(), reading.getIsCompleted());
        return ResponseEntity.ok(readingService.addOrUpdateReading(reading, bookId, userId));
    }

    // User ID로 책 정보 조회
    @GetMapping("/get")
    public ResponseEntity<List<Map<String, Object>>> getBooksByUserId(@RequestParam Long userId) {
        log.info("User ID로 책 정보 조회 요청: userId={}", userId);
        return ResponseEntity.ok(readingService.getBooksByUserId(userId));
    }
}
