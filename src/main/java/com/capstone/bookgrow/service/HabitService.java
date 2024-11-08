package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Habit;
import com.capstone.bookgrow.entity.Reading;
import com.capstone.bookgrow.entity.User;
import com.capstone.bookgrow.repository.HabitRepository;
import com.capstone.bookgrow.repository.ReadingRepository;
import com.capstone.bookgrow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private HabitRepository habitRepository;

    public Habit getHabitByUserId(Long userId) {
        Habit habit = habitRepository.findByUserId(userId);

        if (habit == null) {
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다.")
            );

            habit = new Habit();
            habit.setUser(user);
            habit.setStartDate(LocalDate.now());
            habit.setTotalReadPages(0); // 초기값 설정

            habitRepository.save(habit);
        }

        return habit;
    }

    // 특정 사용자에 대한 총 읽은 페이지 수 계산
    public int calculateTotalReadPages(Long userId) {
        int totalPages = 0;

        List<Reading> userReadings = readingRepository.findByUserId(userId);

        for (Reading reading : userReadings) {
            int pagesRead = reading.getEnd_page() - reading.getStart_page();
            if (pagesRead > 0) {
                totalPages += pagesRead;
            }
        }

        Habit habit = getHabitByUserId(userId);
        habit.setTotalReadPages(totalPages);
        habitRepository.save(habit);

        return totalPages;
    }

    // 일별 평균 독서 쪽 수 계산
    public double calculateAveragePagesPerDay(Habit habit) {
        LocalDate startDate = habit.getStartDate();
        int totalReadPages = habit.getTotalReadPages();
        long totalDays = ChronoUnit.DAYS.between(startDate, LocalDate.now()) + 1;

        // totalDays가 0이면 totalReadPages 반환, 아니면 평균 계산
        if (totalDays > 0) {
            return (double) totalReadPages / totalDays;
        } else {
            return (double) totalReadPages;
        }
    }

    // 쪽별 걸린 시간 계산
    public double calculateTimePerPage(Habit habit) {
        int totalReadPages = habit.getTotalReadPages();
        int totalSeconds = (habit.getUser().getCumulativeHours() * 3600) +
                (habit.getUser().getCumulativeMinutes() * 60) +
                habit.getUser().getCumulativeSeconds();

        if (totalReadPages > 0 && totalSeconds > 0) {
            return (double) totalSeconds / totalReadPages;
        } else {
            return 0.0;
        }
    }

    public int calculateTotalReadPagesAndUpdateHabit(Long userId) {
        int totalPages = calculateTotalReadPages(userId);
        Habit habit = getHabitByUserId(userId);
        habit.setTotalReadPages(totalPages);
        habitRepository.save(habit);

        return totalPages;
    }
}
