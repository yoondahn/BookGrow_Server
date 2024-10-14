package com.capstone.bookgrow.repository;

import com.capstone.bookgrow.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
