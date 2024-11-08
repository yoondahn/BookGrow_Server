package com.capstone.bookgrow.repository;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    // User 객체를 직접 이용해 조회
    List<Reading> findAllByUser(User user);

    List<Reading> findAllByBook(Book book);

    // User의 id를 사용하여 조회하고 싶다면 @Query를 사용합니다.
    @Query("SELECT r FROM Reading r WHERE r.user.id = :userId")
    List<Reading> findByUserId(@Param("userId") Long userId);
}
