package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Category;
import com.capstone.bookgrow.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CategoryInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CategoryInitializer.class);

    private final CategoryRepository categoryRepository;

    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long categoryCount = categoryRepository.count();
        log.info("현재 카테고리 수: {}", categoryCount);

        if (categoryCount == 0) {
            // 기본 카테고리 생성
            Category defaultCategory = new Category();
            defaultCategory.setCategory_name("Default Category");
            defaultCategory.setUser(null);  // user는 null로 설정

            // 기본 카테고리 저장
            categoryRepository.save(defaultCategory);

            log.info("기본 카테고리 생성: 'Default Category'");
        }
    }
}
