package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.Category;
import com.capstone.bookgrow.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookgrow/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 카테고리 추가
    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestParam Long userId, @RequestParam String categoryName) {
        log.info("카테고리 추가 요청: {}, {}", userId, categoryName);

        Category category = new Category();
        category.setId(userId);
        category.setCategory_name(categoryName);

        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    // 카테고리 수정
    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestParam Long id, @RequestParam String categoryName) {
        log.info("카테고리 수정 요청: {}, {}", id, categoryName);
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryName));
    }

    // 전체 조회
    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategories(@RequestParam Long userId) {
        log.info("카테고리 전체 조회 요청: {}", userId);
        return ResponseEntity.ok(categoryService.getAllCategories(userId));
    }
}
