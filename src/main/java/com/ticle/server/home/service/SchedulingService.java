package com.ticle.server.home.service;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.domain.type.Day;
import com.ticle.server.home.repository.SubscriptionRepository;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.exception.PostNotFoundException;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.ticle.server.post.exception.errorcode.PostErrorCode.POST_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SchedulingService {

    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 0 * * *")
    public void sendWeeklyPosts() throws Exception {
        LocalDate today = LocalDate.now();
        List<Subscription> subscriptions = subscriptionRepository.findBySubsDay(Day.valueOf(today.getDayOfWeek().toString()));

        for (Subscription subscription : subscriptions) {
            Post latestPost = postRepository.findLatestPostByUserCategory(subscription.getUser().getCategory())
                    .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));

            emailService.sendEmail(subscription.getEmail(), latestPost);
        }
    }
}