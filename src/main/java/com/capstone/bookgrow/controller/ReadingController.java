package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.service.ReadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookgrow/reading")
@Slf4j
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    // Reading 추가
    @PostMapping("/add")
    public ResponseEntity<Reading> addReading(@RequestBody Reading reading, @RequestParam Long bookId, @RequestParam Long userId) {
        log.info("Reading 추가 요청: bookId={}, userId={}, review={}", bookId, userId, reading.getReview());
        return ResponseEntity.ok(readingService.addReading(reading, bookId, userId));
    }

    // Book ID로 Reading 조회
    @GetMapping("/get")
    public ResponseEntity<Reading> getReadingByBookId(@RequestParam Long bookId) {
        log.info("Reading 조회 요청: {}", bookId);
        return ResponseEntity.ok(readingService.getReadingByBookId(bookId));
    }
}
