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

    // 책 등록
    public Book registerBook(Book book) {
        Optional<Category> category = categoryRepository.findById(1L); // category_id는 항상 default
        category.ifPresent(book::setCategory);
        return bookRepository.save(book);
    }

    // 책 수정
    public Book updateBook(Long id, Long categoryId, Boolean isCompleted) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            // categoryId가 존재할 경우만 수정
            if (categoryId != null) {
                Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                categoryOptional.ifPresent(book::setCategory);
            }

            // isCompleted가 존재할 경우만 수정
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

    // 모든 책 조회
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
