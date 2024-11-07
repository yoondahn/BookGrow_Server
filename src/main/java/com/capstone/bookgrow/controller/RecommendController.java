package com.capstone.bookgrow.controller;

import com.capstone.bookgrow.entity.Recommend;
import com.capstone.bookgrow.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookgrow/recommend")
@Slf4j
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile imageFile) {
        log.info("이미지 업로드 요청을 받았습니다.");
        long imageSize = imageFile.getSize();
        log.info("이미지 크기: {} 바이트", imageSize);

        try {
            String recognizedEmotion = recommendService.sendImageToMLServer(imageFile);

            if (recognizedEmotion != null) {
                log.info("ML 서버로부터 받은 감정 인식 결과: {}", recognizedEmotion);
                return ResponseEntity.ok(recognizedEmotion);
            } else {
                log.error("ML 서버로부터 감정 인식 결과를 받지 못했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("감정 인식에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("이미지 전송 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("이미지 전송 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/getBooks")
    public ResponseEntity<?> getBooks(@RequestParam String emotion) {
        log.info("감정에 따른 도서 추천 요청을 받았습니다. 감정: {}", emotion);

        if ("무표정".equals(emotion)) {
            List<Map<String, String>> bestsellerList = recommendService.sendRequestToMLServer();
            if (bestsellerList != null) {
                log.info("ML 서버로부터 받은 베스트셀러 정보: {}", bestsellerList);
                return ResponseEntity.ok(bestsellerList);
            } else {
                log.error("ML 서버로부터 베스트셀러 정보를 받지 못했습니다.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("ML 서버로부터 베스트셀러 정보를 받지 못했습니다.");
            }
        }

        List<Recommend> recommendations = recommendService.getBooksByEmotion(emotion);
        if (recommendations.isEmpty()) {
            log.warn("해당 감정에 대한 추천 도서가 없습니다: {}", emotion);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("해당 감정에 대한 추천 도서가 없습니다.");
        }

        List<Map<String, String>> response = recommendations.stream()
                .map(book -> Map.of(
                        "title", book.getTitle(),
                        "image", book.getImage(),
                        "isbn", book.getIsbn()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
