package com.capstone.bookgrow.repository;

import com.capstone.bookgrow.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    // book_id로 Reading 엔티티를 조회하는 메서드
    Optional<Reading> findByBookId(Long bookId);
}
