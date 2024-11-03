package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Book;
import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.entity.User;
import com.capstone.bookgrow.repository.ReadingRepository;
import com.capstone.bookgrow.repository.BookRepository;
import com.capstone.bookgrow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Reading 추가
    public Reading addReading(Reading reading, Long bookId, Long userId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (bookOptional.isPresent() && userOptional.isPresent()) {
            reading.setBook(bookOptional.get());
            Reading savedReading = readingRepository.save(reading);

            // 누적 시간 업데이트
            User user = userOptional.get();
            updateCumulativeTime(user, reading.getTotal_time());

            return savedReading;
        } else {
            throw new IllegalArgumentException("해당 ID의 책 또는 사용자가 존재하지 않습니다.");
        }
    }

    // Book ID로 Reading 조회
    public Reading getReadingByBookId(Long bookId) {
        return readingRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 책 ID로 독서 기록을 찾을 수 없습니다."));
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
