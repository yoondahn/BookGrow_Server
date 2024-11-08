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
    public Reading addOrUpdateReading(Reading reading, Long bookId, Long userId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (bookOptional.isPresent() && userOptional.isPresent()) {
            Book book = bookOptional.get();
            User user = userOptional.get();

            List<Reading> allReadings = readingRepository.findAll();
            Reading existingReading = allReadings.stream()
                    .filter(r -> r.getBook().equals(book) && r.getUser().equals(user))
                    .findFirst()
                    .orElse(null);

            if (existingReading != null) {
                List<String> currentReviewList = new ArrayList<>(existingReading.getReview());
                List<String> newReviewList = reading.getReview();

                currentReviewList.addAll(newReviewList);
                existingReading.setReview(currentReviewList);
                existingReading.setTime(reading.getTime());

                // 기존 end_page 값을 start_page로 설정
                existingReading.setStart_page(existingReading.getEnd_page());

                // 새롭게 받아온 end_page 값을 업데이트
                existingReading.setEnd_page(reading.getEnd_page());

                existingReading.setIsCompleted(reading.getIsCompleted());
                reading = existingReading;
            } else {
                reading.setBook(book);
                reading.setUser(user);
                reading.setStart_page(0);
                logger.info("새로운 Reading 엔티티 생성 - bookId: {}, userId: {}", bookId, userId);
            }

            book.setCurrent_page(reading.getEnd_page());
            bookRepository.save(book);

            Reading savedReading = readingRepository.save(reading);

            updateCumulativeTime(user, reading.getTime());

            if (Boolean.TRUE.equals(reading.getIsCompleted())) { // 완료 여부가 true이면 업데이트
                user.setComplete(user.getComplete() + 1);
                user.setFlower(user.getFlower() + 1);

                // flower가 3의 배수일 경우 domination 증가
                if (user.getFlower() % 3 == 0) {
                    user.setDomination(user.getDomination() + 1);
                }

                userRepository.save(user);
            }

            return savedReading;
        } else {
            throw new IllegalArgumentException("해당 ID의 책 또는 사용자가 존재하지 않습니다.");
        }
    }

    // userId로 연결된 책 정보를 반환하는 메서드 (isCompleted가 false인 경우만 포함)
    public List<Map<String, Object>> getBooksByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다.");
        }

        User user = userOptional.get();
        List<Reading> readings = readingRepository.findAllByUser(user);
        List<Map<String, Object>> bookInfoList = new ArrayList<>();

        for (Reading reading : readings) {
            if (Boolean.FALSE.equals(reading.getIsCompleted())) {  // isCompleted가 false인 경우만 반환
                Book book = reading.getBook();
                Map<String, Object> bookInfo = new HashMap<>();
                bookInfo.put("bookId", book.getId());
                bookInfo.put("title", book.getTitle());
                bookInfo.put("image_url", book.getImage_url());
                bookInfo.put("current_page", book.getCurrent_page());
                bookInfo.put("total_page", book.getTotal_page());
                bookInfoList.add(bookInfo);
            }
        }

        return bookInfoList;
    }

    // 누적 시간 업데이트 메서드
    private void updateCumulativeTime(User user, String time) {
        // time이 null인 경우 기본값 "00:00:00" 설정
        if (time == null) {
            time = "00:00:00";

            // time 파싱
            String[] timeParts = time.split(":");
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
}
