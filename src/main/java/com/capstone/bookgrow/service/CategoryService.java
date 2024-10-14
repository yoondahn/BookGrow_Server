package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Category;
import com.capstone.bookgrow.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // 전체 조회
    public List<Category> getAllCategories(Long userId) {
        return categoryRepository.findAllByUserId(userId);
    }

    // 카테고리 추가
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    // 카테고리 수정
    public Category updateCategory(Long id, String categoryName) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setCategory_name(categoryName);  // 카테고리명 수정
            return categoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("카테고리가 존재하지 않습니다.");
        }
    }
}
