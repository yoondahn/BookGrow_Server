package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.service.BookService;
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

    // 도서 등록
    @PostMapping("/register")
    public ResponseEntity<Book> registerBook(@RequestBody Book book) {
        log.info("도서 등록 요청: {}, {}", book.getTitle(), book.getAuthor());
        return ResponseEntity.ok(bookService.registerBook(book));
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

    // 전체 도서 조회
    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("전체 도서 조회 요청");
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
