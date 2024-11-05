package com.capstone.bookgrow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.converter.StringHttpMessageConverter;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/bookgrow/recommend")
@Slf4j
public class RecommendController {

    private static final String ML_SERVER_URL = "http://192.168.35.135:5000/upload_image";

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile imageFile) {
        log.info("이미지 업로드 요청을 받았습니다.");
        long imageSize = imageFile.getSize();
        log.info("이미지 크기: {} 바이트", imageSize);

        try {
            // 이미지 파일을 ML 서버로 전송하여 감정 인식 결과 받기
            String recognizedEmotion = sendImageToMLServer(imageFile);

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

    // UTF-8 인코딩을 지원하는 RestTemplate 설정
    private RestTemplate createUtf8RestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    // ML 서버로 이미지 전송 메서드
    private String sendImageToMLServer(MultipartFile imageFile) {
        try {
            RestTemplate restTemplate = createUtf8RestTemplate(); // UTF-8 설정 적용

            // 이미지 파일을 ByteArray로 변환
            ByteArrayResource imageResource = new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            };

            // 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("imageFile", imageResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // ML 서버에 요청 전송
            ResponseEntity<String> response = restTemplate.postForEntity(ML_SERVER_URL, requestEntity, String.class);

            // 응답 결과 확인
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("ML 서버에서 오류 응답: {}", response.getStatusCode());
                return null;
            }

        } catch (Exception e) {
            log.error("ML 서버로 이미지 전송 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }
}
