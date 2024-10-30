package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.User;
import com.capstone.bookgrow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    // 아이디 중복 확인
    @GetMapping("/checkId")
    public ResponseEntity<Boolean> checkDuplicate(@RequestParam String register_id) {
        log.info("아이디 중복 확인 요청: {}", register_id);
        boolean isDuplicate = userService.isRegisterIdDuplicate(register_id);
        return ResponseEntity.ok(isDuplicate);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String registerId, @RequestParam String password) {
        log.info("로그인 요청: {}", registerId);
        User user = userService.login(registerId, password);

        if (user != null) {
            // 필요한 필드만 포함하는 Map 객체 생성
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("registerId", user.getRegisterId());
            userData.put("password", user.getPassword());
            userData.put("name", user.getName());
            userData.put("nickname", user.getNickname());

            // success와 함께 userData를 반환
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("user", userData);
            return ResponseEntity.ok(response);
        } else {
            // 로그인 실패 시 success: false 반환
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // 회원 조회
    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam Long id) {
        log.info("회원 조회 요청: {}", id);
        return ResponseEntity.ok(userService.getUser(id));
    }
}
