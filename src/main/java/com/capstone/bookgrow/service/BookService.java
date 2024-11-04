package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.entity.Category;
import com.capstone.bookgrow.entity.User;
import com.capstone.bookgrow.repository.BookRepository;
import com.capstone.bookgrow.repository.CategoryRepository;
import com.capstone.bookgrow.repository.ReadingRepository;
import com.capstone.bookgrow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    // 책 등록
    public Book registerBook(Book book, Long userId) {
        book.setUserId(userId);

        // 기본 카테고리 설정
        Optional<Category> category = categoryRepository.findById(1L);
        category.ifPresent(book::setCategory);

        // 책 등록
        Book savedBook = bookRepository.save(book);

        // 소유 도서 수 증가 로직
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setOwned(user.getOwned() + 1); // owned 필드 +1 증가
            userRepository.save(user);
        }

        return savedBook;
    }

    // 책 수정
    public Book updateBook(Long id, Long categoryId, Boolean isCompleted) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            if (categoryId != null) {
                Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                categoryOptional.ifPresent(book::setCategory);
            }

            if (isCompleted != null) {
                book.setIs_completed(isCompleted);
            }

            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("해당 ID의 책이 존재하지 않습니다.");
        }
    }

    // 책 조회
    public Map<String, Object> getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 책이 존재하지 않습니다."));

        // 책의 상세 정보를 맵으로 준비
        Map<String, Object> bookInfo = new HashMap<>();
        bookInfo.put("imageUrl", book.getImage_url());
        bookInfo.put("title", book.getTitle());
        bookInfo.put("author", book.getAuthor());
        bookInfo.put("publisher", book.getPublisher());
        bookInfo.put("publishedDate", book.getPublished_year());
        bookInfo.put("isbn", book.getIsbn());
        bookInfo.put("currentPage", book.getCurrent_page());
        bookInfo.put("totalPage", book.getTotal_page());

        // Reading 엔티티에서 리뷰 리스트 가져오기
        List<String> reviewList = readingRepository.findAllByBook(book)
                .stream()
                .flatMap(reading -> reading.getReview().stream())
                .collect(Collectors.toList());

        bookInfo.put("review", reviewList);

        // 로그 출력
        System.out.println("조회한 책 정보: " + bookInfo);
        System.out.println("리뷰 리스트: " + reviewList);

        return bookInfo;
    }

    // 사용자별 모든 책 조회
    public List<Book> getAllBooks(Long userId) {
        return bookRepository.findByUserId(userId);
    }
}
