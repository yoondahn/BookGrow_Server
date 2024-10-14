package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.repository.ReadingRepository;
import com.capstone.bookgrow.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private BookRepository bookRepository;

    // Reading 추가
    public Reading addReading(Reading reading, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isPresent()) {
            reading.setBook(bookOptional.get());
            return readingRepository.save(reading);
        } else {
            throw new IllegalArgumentException("해당 ID의 책이 존재하지 않습니다.");
        }
    }

    // Book ID로 Reading 조회
    public Reading getReadingByBookId(Long bookId) {
        return readingRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 책 ID로 독서 기록을 찾을 수 없습니다."));
    }
}
