package com.capstone.bookgrow.repository;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    List<Reading> findAllByUser(User user);

    List<Reading> findAllByBook(Book book);
}
