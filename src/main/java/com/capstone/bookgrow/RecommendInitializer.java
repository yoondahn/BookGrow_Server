package com.capstone.bookgrow;

import com.capstone.bookgrow.entity.Recommend;
import com.capstone.bookgrow.repository.RecommendRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class RecommendInitializer {

    private final RecommendRepository recommendRepository;

    @PostConstruct
    public void initializeRecommendations() {
        // 데이터베이스가 비어 있는 경우에만 기본 데이터 삽입
        if (recommendRepository.count() == 0) {
            List<Recommend> recommendations = getDefaultRecommendations();
            recommendRepository.saveAll(recommendations);
            log.info("기본 추천 데이터가 데이터베이스에 저장되었습니다.");
        } else {
            log.info("데이터베이스에 추천 데이터가 이미 존재합니다.");
        }
    }

    // 기본 추천 도서 목록 정의
    private List<Recommend> getDefaultRecommendations() {
        List<Recommend> recommendations = new ArrayList<>();

        // "행복" 감정의 기본 도서 목록
        recommendations.add(new Recommend("행복", "행복예습", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/4801185716641.jpg", "9791185716640"));
        recommendations.add(new Recommend("행복", "꾸빼씨의 행복 여행", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788995501443.jpg", "9788970638881"));
        recommendations.add(new Recommend("행복", "아주 보통의 행복 - 평범해서 더욱 소중한", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788950996437.jpg", "9788950996437"));
        recommendations.add(new Recommend("행복", "무지개 곶의 찻집", "https://image.yes24.com/momo/TopCate188/MidCate03/18727087.jpg", "9788946418233"));
        recommendations.add(new Recommend("행복", "행복한 사람의 DNA는 무엇이 다른가", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788995845776.jpg", "9788995845776"));
        recommendations.add(new Recommend("행복", "이유없이 행복하라", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788960170728.jpg", "9788960170728"));
        recommendations.add(new Recommend("행복", "어른의 행복은 조용하다", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791169851053.jpg", "9791169851053"));
        recommendations.add(new Recommend("행복", "완전한 행복", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791167370280.jpg", "9791167370280"));
        recommendations.add(new Recommend("행복", "노르웨이의 숲", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788937434488.jpg", "9788937434488"));
        recommendations.add(new Recommend("행복", "행복의 조건", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788947527385.jpg", "9788947527385"));
        recommendations.add(new Recommend("행복", "행복의 지도 – 세상에서 가장 행복한 곳을 찾아 떠난 여행", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791167740021.jpg", "9791167740021"));
        recommendations.add(new Recommend("행복", "우리도 행복할 수 있을까", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788997780136.jpg", "9788997780136"));
        recommendations.add(new Recommend("행복", "행복의 과학", "https://image.yes24.com/goods/129048438/XL", "9788967440985"));
        recommendations.add(new Recommend("행복", "행복할 거야 이래도 되나 싶을 정도로", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791162145043.jpg", "9791162145043"));
        recommendations.add(new Recommend("행복", "행복의 기원", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791171175864.jpg", "9791171175864"));

        // "슬픔" 감정의 기본 도서 목록
        recommendations.add(new Recommend("슬픔", "우울한 거지 불행한 게 아니에요", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791188090297.jpg", "9791188090297"));
        recommendations.add(new Recommend("슬픔", "참으로 마음이 행복해지는 책 : 걱정과 고민이 많은 당신에게", "https://image.yes24.com/goods/122787356/XL", "9791190312912"));
        recommendations.add(new Recommend("슬픔", "괜찮아, 괜찮아 그래도 괜찮아", "https://image.yes24.com/momo/TopCate641/MidCate001/64003829.jpg", "9791157669257"));
        recommendations.add(new Recommend("슬픔", "실격당한 자들을 위한 변론", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791160943733.jpg", "9791160943733"));
        recommendations.add(new Recommend("슬픔", "바다의 뚜껑", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788937433207.jpg", "9788937433207"));
        recommendations.add(new Recommend("슬픔", "이 슬픔이 슬픈 채로 끝나지 않기를", "https://image.yes24.com/goods/35296081/XL", "9788925560854"));
        recommendations.add(new Recommend("슬픔", "두근 두근 내 인생", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/4808936433871.jpg", "9788936433871"));
        recommendations.add(new Recommend("슬픔", "하루하루가 이별의 날", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791130613208.jpg", "9791130613208"));
        recommendations.add(new Recommend("슬픔", "나는 나로 살기로 했다", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791197377150.jpg", "9791187119845"));
        recommendations.add(new Recommend("슬픔", "그래도 괜찮은 하루", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788959138876.jpg", "9788959138876"));
        recommendations.add(new Recommend("슬픔", "살면서 쉬웠던 날은 단 하루도 없었다", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788959139477.jpg", "9788959139477"));
        recommendations.add(new Recommend("슬픔", "참 애썼다 그것으로 되었다", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/4801162141886.jpg", "9791162141885"));
        recommendations.add(new Recommend("슬픔", "1cm 다이빙", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791190299060.jpg", "9791190299060"));
        recommendations.add(new Recommend("슬픔", "실컷 울고나니 배고파졌어요", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791161659534.jpg", "9791161659534"));
        recommendations.add(new Recommend("슬픔", "어쩔 수 없는 힘듦이 내게 찾아왔다면", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791196797720.jpg", "9791196797720"));
        recommendations.add(new Recommend("슬픔", "하버드 회복탄성력 수업", "https://image.yes24.com/goods/101873316/XL", "9791166815782"));

        // "놀람" 감정의 기본 도서 목록
        recommendations.add(new Recommend("놀람", "불안의 밤에 고하는 말 - 세상의 소음으로부터 서서히 멀어지는 연습", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791168125162.jpg", "9791168125162"));
        recommendations.add(new Recommend("놀람", "리틀 포레스트", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788983719713.jpg", "9788937491717"));
        recommendations.add(new Recommend("놀람", "정리하는 뇌", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788937837654.jpg", "9788937837654"));
        recommendations.add(new Recommend("놀람", "고래는 물에서 숨 쉬지 않는다", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791193506530.jpg", "9791193506530"));
        recommendations.add(new Recommend("놀람", "감사의 재발견", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791139701319.jpg", "9791139701319"));
        recommendations.add(new Recommend("놀람", "불안", "https://image.yes24.com/goods/6111280/XL", "9788956605593"));
        recommendations.add(new Recommend("놀람", "연필의 101가지 사용법", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791186757185.jpg", "9791186757185"));
        recommendations.add(new Recommend("놀람", "위대한 나의 발견 강점혁형", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788935213573.jpg", "9788935213573"));
        recommendations.add(new Recommend("놀람", "운동의 뇌과학", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791139716009.jpg", "9791139716009"));
        recommendations.add(new Recommend("놀람", "매우 작은 세계에서 발견한 뜻밖의 생물학", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791171173167.jpg", "9791171173167"));
        recommendations.add(new Recommend("놀람", "느낌의 발견 - 의식을 만들어 내는 몸과 정서", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788950906214.jpg", "9788950906214"));
        recommendations.add(new Recommend("놀람", "피타고라스 생각 수업", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791191104578.jpg", "9791191104578"));
        recommendations.add(new Recommend("놀람", "평소의 발견 - 카피라이터 유병욱이 말하는 평소와 관찰, 메모, 음악, 믿음", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791164050321.jpg", "9791164050321"));
        recommendations.add(new Recommend("놀람", "유레카 NLP", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791195585564.jpg", "9791188393474"));

        // "화남" 감정의 기본 도서 목록
        recommendations.add(new Recommend("화남", "보통의 분노", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791192641461.jpg", "9791192641461"));
        recommendations.add(new Recommend("화남", "분노 수업", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788984076549.jpg", "9788934905516"));
        recommendations.add(new Recommend("화남", "감정 사용 설명서", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/450D000086872.jpg", "9788952233902"));
        recommendations.add(new Recommend("화남", "오만과 편견", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9791190818315.jpg", "9788972756190"));
        recommendations.add(new Recommend("화남", "사람을 움직이는 대화의 기술", "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788988922712.jpg", "9788991944289"));
        recommendations.add(new Recommend("화남", "어떻게 살 것인가", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788965132288.jpg", "9788934956051"));
        recommendations.add(new Recommend("화남", "감정의 재발견", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788947540681.jpg", "9791190604260"));
        recommendations.add(new Recommend("화남", "누구도 나를 화나게 하지 않았다", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791188167678.jpg", "9791188167678"));
        recommendations.add(new Recommend("화남", "악한 분노, 선한 분노", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788977824171.jpg", "9788977824171"));
        recommendations.add(new Recommend("화남", "나를 바꾸는 분노조절", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788991907508.jpg", "9788991907508"));
        recommendations.add(new Recommend("화남", "정의감 중독 사회", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791198127921.jpg", "9791198127921"));
        recommendations.add(new Recommend("화남", "어떻게 분노를 다스릴 것인가?", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791187147626.jpg", "9791187147626"));
        recommendations.add(new Recommend("화남", "뭘 해도 잘되는 사람의 모닝 루틴", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9791190312479.jpg", "9791190312479"));
        recommendations.add(new Recommend("화남", "평정심, 나를 지켜내는 힘", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788993635553.jpg", "9788993635553"));
        recommendations.add(new Recommend("화남", "모기 뒤에 숨은 코끼리", "https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788947547444.jpg", "9788947598873"));

        return recommendations;
    }
}
