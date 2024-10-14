package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.User;
import com.capstone.bookgrow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 회원가입 로직
    public User register(User user) {
        // register_id 중복 체크
        Optional<User> existingUser = userRepository.findByRegisterId(user.getRegisterId());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자 ID입니다.");
        }

        // 비밀번호 암호화 등을 추가할 수 있음
        return userRepository.save(user);
    }

    // 로그인 로직
    public User login(String registerId, String password) {
        // register_id와 password로 사용자 조회
        return userRepository.findByRegisterIdAndPassword(registerId, password)
                .orElseThrow(() -> new IllegalArgumentException("로그인 정보가 일치하지 않습니다."));
    }

    // 회원 조회 로직
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다."));
    }
}
