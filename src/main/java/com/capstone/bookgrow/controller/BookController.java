package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.service.BookService;
import com.capstone.bookgrow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookgrow/book")
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    // 도서 등록
    @PostMapping("/register")
    public ResponseEntity<Book> registerBook(@RequestBody Book book,
                                             @RequestParam Long userId,
                                             @RequestParam String isCompleted) {
        log.info("도서 등록 요청: {}, {}, isCompleted={}", book.getTitle(), book.getAuthor(), isCompleted);

        // 도서 등록
        Book registeredBook = bookService.registerBook(book, userId);

        // isCompleted 값이 'Y'인 경우 complete 필드 +1
        if ("Y".equalsIgnoreCase(isCompleted)) {
            userService.incrementCompleteField(userId);
        }

        return ResponseEntity.ok(registeredBook);
    }
    // 도서 수정
    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestParam Long id,
                                           @RequestParam(required = false) Long categoryId,
                                           @RequestParam(required = false) Boolean isCompleted) {
        log.info("도서 수정 요청: {}, {}, {}", id, categoryId, isCompleted);
        return ResponseEntity.ok(bookService.updateBook(id, categoryId, isCompleted));
    }

    // 도서 조회
    @GetMapping("/get")
    public ResponseEntity<Book> getBook(@RequestParam Long id) {
        log.info("도서 조회 요청: {}", id);
        return ResponseEntity.ok(bookService.getBook(id));
    }

    // 사용자별 도서 조회
    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam Long userId) {
        log.info("사용자별 도서 조회 요청: userId={}", userId);
        return ResponseEntity.ok(bookService.getAllBooks(userId));
    }
}
