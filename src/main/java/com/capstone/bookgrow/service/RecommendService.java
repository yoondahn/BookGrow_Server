package com.capstone.bookgrow.service;

import com.capstone.bookgrow.entity.Recommend;
import com.capstone.bookgrow.repository.RecommendRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService {

    @Value("${ml.server.url}")
    private String ML_SERVER_URL;

    private final RecommendRepository recommendRepository;

    // 이미지 파일을 ML 서버로 전송하여 감정 인식 결과 받기
    public String sendImageToMLServer(MultipartFile imageFile) {
        try {
            RestTemplate restTemplate = createUtf8RestTemplate();

            ByteArrayResource imageResource = new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            };

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("imageFile", imageResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String fullUrl = ML_SERVER_URL + "/upload_image";
            log.info("ML 서버로 이미지 전송 요청: {}", fullUrl);

            ResponseEntity<String> response = restTemplate.postForEntity(fullUrl, requestEntity, String.class);

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

    // UTF-8 인코딩을 지원하는 RestTemplate 설정
    private RestTemplate createUtf8RestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    // emotion에 따라 추천 도서를 가져오고, 무작위로 선택하여 도서 정보 반환
    public List<Recommend> getBooksByEmotion(String emotion) {
        List<Recommend> books = recommendRepository.findByEmotion(emotion);

        // 감정에 해당하는 도서 목록이 비어있지 않을 경우 무작위로 선택
        if (!books.isEmpty() && books.size() > 7) {
            Collections.shuffle(books);
            return books.subList(0, 7);
        }

        return books;
    }

    // 감정이 '무표정'일 경우 ML 서버에서 베스트셀러 정보 요청
    public List<Map<String, String>> sendRequestToMLServer() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String fullUrl = ML_SERVER_URL + "/get_bestsellers";

            log.info("ML 서버에 베스트셀러 정보 요청 중: {}", fullUrl);

            ResponseEntity<String> response = restTemplate.getForEntity(fullUrl, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("ML 서버에서 받은 베스트셀러 정보: {}", response.getBody());

                // JSON 응답 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonArray = objectMapper.readTree(response.getBody());

                List<Map<String, String>> bestsellerInfoList = new ArrayList<>();
                for (JsonNode book : jsonArray) {
                    String title = book.get("title").asText();
                    String imageUrl = book.get("image").asText();

                    Map<String, String> bookInfo = new HashMap<>();
                    bookInfo.put("title", title);
                    bookInfo.put("image", imageUrl);

                    bestsellerInfoList.add(bookInfo);
                }

                return bestsellerInfoList;
            } else {
                log.error("ML 서버에서 베스트셀러 정보 요청에 대한 오류 응답: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            log.error("ML 서버로 베스트셀러 정보 요청 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }
}
