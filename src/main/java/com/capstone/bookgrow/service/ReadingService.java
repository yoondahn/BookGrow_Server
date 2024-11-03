package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.entity.User;
import com.capstone.bookgrow.repository.ReadingRepository;
import com.capstone.bookgrow.repository.BookRepository;
import com.capstone.bookgrow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class ReadingService {

    private static final Logger logger = LoggerFactory.getLogger(ReadingService.class);

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Reading 추가 또는 업데이트
    public Reading addOrUpdateReading(Reading reading, Long bookId, Long userId, Boolean isCompleted) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (bookOptional.isPresent() && userOptional.isPresent()) {
            Book book = bookOptional.get();
            User user = userOptional.get();

            // 모든 Reading 엔티티 가져와서 bookId와 userId로 필터링
            List<Reading> allReadings = readingRepository.findAll();
            Reading existingReading = allReadings.stream()
                    .filter(r -> r.getBook().equals(book) && r.getUser().equals(user))
                    .findFirst()
                    .orElse(null);

            if (existingReading != null) {
                // 기존 리뷰 리스트 로그 출력
                List<String> currentReviewList = new ArrayList<>(existingReading.getReview()); // 복사본 생성
                List<String> newReviewList = reading.getReview();

                logger.info("기존 리뷰: {}", currentReviewList);
                logger.info("새로운 리뷰: {}", newReviewList);

                // 기존 리뷰 리스트에 새로운 리뷰 추가
                currentReviewList.addAll(newReviewList);
                existingReading.setReview(currentReviewList);  // 명시적으로 전체 리뷰 리스트를 설정
                existingReading.setTotal_time(reading.getTotal_time());
                existingReading.setEnd_page(reading.getEnd_page());
                reading = existingReading; // 기존 엔티티에 업데이트

                logger.info("업데이트된 리뷰 리스트: {}", existingReading.getReview());
            } else {
                // 새로운 Reading 엔티티 설정
                reading.setBook(book);
                reading.setUser(user);
                logger.info("새로운 Reading 엔티티 생성 - bookId: {}, userId: {}", bookId, userId);
            }

            // end_page를 current_page로 업데이트
            book.setCurrent_page(String.valueOf(reading.getEnd_page()));
            bookRepository.save(book);

            // Reading 엔티티 저장 또는 업데이트
            Reading savedReading = readingRepository.save(reading);

            // 누적 시간 업데이트
            updateCumulativeTime(user, reading.getTotal_time());

            // 만약 isCompleted가 true이면, complete와 flower 필드를 증가
            if (isCompleted) {
                user.setComplete(user.getComplete() + 1);
                user.setFlower(user.getFlower() + 1);
                userRepository.save(user);
            }

            return savedReading;
        } else {
            throw new IllegalArgumentException("해당 ID의 책 또는 사용자가 존재하지 않습니다.");
        }
    }

    // userId로 연결된 책 정보를 반환하는 메서드
    public List<Map<String, Object>> getBooksByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다.");
        }

        User user = userOptional.get();
        List<Reading> readings = readingRepository.findAllByUser(user);
        List<Map<String, Object>> bookInfoList = new ArrayList<>();

        for (Reading reading : readings) {
            Book book = reading.getBook();
            Map<String, Object> bookInfo = new HashMap<>();
            bookInfo.put("bookId", book.getId());
            bookInfo.put("title", book.getTitle());
            bookInfo.put("image_url", book.getImage_url());
            bookInfo.put("current_page", book.getCurrent_page());
            bookInfo.put("total_page", book.getTotal_page());
            bookInfoList.add(bookInfo);
        }

        return bookInfoList;
    }

    // 누적 시간 업데이트 메서드
    private void updateCumulativeTime(User user, String totalTime) {
        String[] timeParts = totalTime.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);

        // 현재 누적 시간 가져오기
        int currentHours = user.getCumulativeHours();
        int currentMinutes = user.getCumulativeMinutes();
        int currentSeconds = user.getCumulativeSeconds();

        // 새로운 누적 시간 계산
        int newSeconds = currentSeconds + seconds;
        int extraMinutes = newSeconds / 60;
        newSeconds = newSeconds % 60;

        int newMinutes = currentMinutes + minutes + extraMinutes;
        int extraHours = newMinutes / 60;
        newMinutes = newMinutes % 60;

        int newHours = currentHours + hours + extraHours;

        // 누적 시간 업데이트
        user.setCumulativeHours(newHours);
        user.setCumulativeMinutes(newMinutes);
        user.setCumulativeSeconds(newSeconds);

        // 사용자 업데이트
        userRepository.save(user);
    }
}
