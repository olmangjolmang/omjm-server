package com.ticle.server.opinion.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.opinion.repository.OpinionRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class OpinionInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final OpinionRepository opinionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (opinionRepository.count() > 0) {
            log.info("[Opinion] 더미 데이터 존재");
        } else {
            List<Opinion> opinionList = new ArrayList<>();

            Opinion opinion1 = Opinion.builder()
                    .question("좋은 리더란 어떤 자질이 필요할까요?")
                    .viewCount(5L)
                    .commentCount(1L)
                    .build();

            Opinion opinion2 = Opinion.builder()
                    .question("팀에서 가장 중요한 구성원은 누구인가요?")
                    .viewCount(10L)
                    .commentCount(3L)
                    .build();

            Opinion opinion3 = Opinion.builder()
                    .question("롤모델이 있다면 누구이고 이유는 무엇인가요?")
                    .viewCount(14L)
                    .commentCount(2L)
                    .build();

            Opinion opinion4 = Opinion.builder()
                    .question("인생에서 가장 필요한 사항은 무엇이라 생각하나요?")
                    .viewCount(20L)
                    .commentCount(2L)
                    .build();

            Opinion opinion5 = Opinion.builder()
                    .question("대인관계에서 가장 중요하게 생각하는 것은 무엇인가요?")
                    .viewCount(4L)
                    .commentCount(2L)
                    .build();

            Opinion opinion6 = Opinion.builder()
                    .question("가장 기억에 남는 프로젝트는 무엇인가요?")
                    .viewCount(12L)
                    .commentCount(0L)
                    .build();

            Opinion opinion7 = Opinion.builder()
                    .question("성공적인 팀워크를 위해 가장 필요한 요소는 무엇인가요?")
                    .viewCount(8L)
                    .commentCount(0L)
                    .build();

            Opinion opinion8 = Opinion.builder()
                    .question("기술 발전이 사회에 미치는 영향은 무엇인가요?")
                    .viewCount(15L)
                    .commentCount(0L)
                    .build();

            Opinion opinion9 = Opinion.builder()
                    .question("효과적인 문제 해결 방법은 무엇이라 생각하나요?")
                    .viewCount(9L)
                    .commentCount(0L)
                    .build();

            Opinion opinion10 = Opinion.builder()
                    .question("업무에서 동기부여를 유지하는 방법은 무엇인가요?")
                    .viewCount(18L)
                    .commentCount(0L)
                    .build();

            Opinion opinion11 = Opinion.builder()
                    .question("IT 업계에서 가장 중요한 기술 트렌드는 무엇인가요?")
                    .viewCount(11L)
                    .commentCount(0L)
                    .build();

            Opinion opinion12 = Opinion.builder()
                    .question("효과적인 의사소통 방법은 무엇이라고 생각하나요?")
                    .viewCount(7L)
                    .commentCount(0L)
                    .build();

            Opinion opinion13 = Opinion.builder()
                    .question("최근에 배운 가장 중요한 교훈은 무엇인가요?")
                    .viewCount(10L)
                    .commentCount(0L)
                    .build();

            Opinion opinion14 = Opinion.builder()
                    .question("미래의 커리어 목표는 무엇인가요?")
                    .viewCount(13L)
                    .commentCount(0L)
                    .build();

            opinionList.add(opinion1);
            opinionList.add(opinion2);
            opinionList.add(opinion3);
            opinionList.add(opinion4);
            opinionList.add(opinion5);
            opinionList.add(opinion6);
            opinionList.add(opinion7);
            opinionList.add(opinion8);
            opinionList.add(opinion9);
            opinionList.add(opinion10);
            opinionList.add(opinion11);
            opinionList.add(opinion12);
            opinionList.add(opinion13);
            opinionList.add(opinion14);

            opinionRepository.saveAll(opinionList);
        }
    }
}
