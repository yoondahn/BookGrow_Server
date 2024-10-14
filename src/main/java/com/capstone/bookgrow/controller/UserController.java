package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.User;
import com.capstone.bookgrow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookgrow/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestParam String register_id, @RequestParam String password,
                                         @RequestParam String name, @RequestParam String nickname) {
        log.info("회원가입 요청: {}, {}, {}", register_id, name, nickname);

        User user = new User();
        user.setRegisterId(register_id);
        user.setPassword(password);
        user.setName(name);
        user.setNickname(nickname);

        return ResponseEntity.ok(userService.register(user));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String userId, @RequestParam String password) {
        log.info("로그인 요청: {}", userId);
        return ResponseEntity.ok(userService.login(userId, password));
    }

    // 회원 조회
    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam Long id) {
        log.info("회원 조회 요청: {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }
}
