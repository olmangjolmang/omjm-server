package com.ticle.server.home.service;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.dto.request.SubscriptionRequest;
import com.ticle.server.home.dto.response.HomeResponse;
import com.ticle.server.home.dto.response.PostSetsResponse;
import com.ticle.server.home.repository.SubscriptionRepository;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.exception.UserNotFoundException;
import com.ticle.server.user.exception.UserNotLoginException;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;
import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_LOGIN;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PostRepository postRepository;
    private final PostTopicCache postTopicCache;

    @Transactional
    public void uploadSubscription(SubscriptionRequest request, CustomUserDetails userDetails) {
        if (ObjectUtils.isEmpty(userDetails)) {
            throw new UserNotLoginException(USER_NOT_LOGIN);
        }

        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        Subscription subscription = request.toSubscription(user);
        subscriptionRepository.save(subscription);
    }

    public List<HomeResponse> generateHomeInfo() {
        List<HomeResponse> responseList = new ArrayList<>();

        // 이번주 TOP 3
        responseList.add(findTop3Posts());
        // 랜덤 3개의 주제와 포스트
        responseList.addAll(find3RandomTopicAndPosts());

        return responseList;
    }

    private HomeResponse findTop3Posts() {
        List<PostSetsResponse> topPosts = postRepository.findTop3ByOrderByScrapCountDesc();
        return HomeResponse.of("이번주 TOP 3", topPosts);
    }


    private List<HomeResponse> find3RandomTopicAndPosts() {
        return postTopicCache.getRandomPosts(3)
                .entrySet().stream()
                .map(entry -> HomeResponse.of(entry.getKey(), entry.getValue()))
                .toList();
    }
}
