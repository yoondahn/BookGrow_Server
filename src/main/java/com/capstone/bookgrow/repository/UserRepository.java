package com.capstone.bookgrow.repository;

import com.capstone.bookgrow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRegisterId(String registerId);  // register_id로 사용자 검색
    Optional<User> findByRegisterIdAndPassword(String registerId, String password);
}
