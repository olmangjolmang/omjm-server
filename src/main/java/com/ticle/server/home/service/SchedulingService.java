package com.ticle.server.home.service;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.domain.type.Day;
import com.ticle.server.home.repository.SubscriptionRepository;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.dto.GeminiRequest;
import com.ticle.server.post.dto.GeminiResponse;
import com.ticle.server.post.exception.PostNotFoundException;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.ticle.server.post.exception.errorcode.PostErrorCode.POST_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulingService implements ApplicationRunner {

    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final EmailService emailService;
    private final RestTemplate restTemplate;
    private final PostTopicCache postTopicCache;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private static final int POST_COUNT = 10;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendWeeklyPosts() {
        LocalDate today = LocalDate.now();
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubsDay(Day.valueOf(today.getDayOfWeek().toString()));

        for (Subscription subscription : subscriptions) {
            // 이번주 가장 인기 있는 글
            Post topPost = postRepository.findTopPostByCategory(subscription.getUser().getCategory())
                    .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));

            emailService.sendEmail(subscription.getEmail(), topPost);
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        generateCommonTitleMultipleTimes();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void generateCommonTitleMultipleTimes() {
        postTopicCache.clearCache();

        int count = (int) postRepository.count();

        for (int i = 0; i < POST_COUNT; i++) {
            saveCommonTitleAndPostsToCache(count);
        }
    }

    private void saveCommonTitleAndPostsToCache(int count) {
        Set<Long> selectedPostIds = getRandomIndices(count);

        // 선택된 post_id에 해당하는 Post 객체 가져오기
        List<Post> selectedPosts = postRepository.findAllById(selectedPostIds);

        // AI에게 3개의 Post 제목을 보내서 공통된 제목 추천받기
        String commonTitle = generateCommonTitle(selectedPosts);

        // commonTitle과 selectedPosts를 PostTopicCache에 저장
        postTopicCache.saveToCache(commonTitle, selectedPosts);
        log.info("post: {}", postTopicCache.getPostsFromCache(commonTitle));
    }

    private String generateCommonTitle(List<Post> selectedPosts) {
        // Gemini에 요청 전송
        String requestUrl = apiUrl + "?key=" + geminiApiKey;

        String prompt = selectedPosts.stream()
                .map(Post::getTitle)
                .toList() + " 다음은 3개의 기사 제목 리스트야. 해당 제목들에 대한 공통제목을 20자 이내로 1개만 추천해줘 " +
                "예: commonTitle = [개발자를 위한 성장 가이드]";

        log.info("prompt: {}", prompt);

        GeminiRequest request = new GeminiRequest(prompt);
        GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);

        log.info("response: {}", response);

        return response.getCommonTitle();
    }

    private Set<Long> getRandomIndices(int count) {
        // 3개의 랜덤한 post_id 선택하기
        Set<Long> selectedPostIds = new HashSet<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        while (selectedPostIds.size() < 3) {
            long randomPostId = random.nextInt(count) + 1L;
            selectedPostIds.add(randomPostId);
        }

        log.info("selectedPostIds: {}", selectedPostIds);
        return selectedPostIds;
    }
}