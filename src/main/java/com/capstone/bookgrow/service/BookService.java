package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.entity.Category;
import com.capstone.bookgrow.repository.BookRepository;
import com.capstone.bookgrow.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 책 등록 (userId 추가)
    public Book registerBook(Book book, Long userId) {
        // userId를 직접 설정
        book.setUserId(userId);

        // 기본 카테고리 설정
        Optional<Category> category = categoryRepository.findById(1L);
        category.ifPresent(book::setCategory);

        return bookRepository.save(book);
    }

    // 책 수정
    public Book updateBook(Long id, Long categoryId, Boolean isCompleted) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            if (categoryId != null) {
                Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                categoryOptional.ifPresent(book::setCategory);
            }

            if (isCompleted != null) {
                book.setIs_completed(isCompleted);
            }

            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("해당 ID의 책이 존재하지 않습니다.");
        }
    }

    // 책 조회
    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 책이 존재하지 않습니다."));
    }

    // 사용자별 모든 책 조회
    public List<Book> getAllBooks(Long userId) {
        return bookRepository.findByUserId(userId);
    }
}
