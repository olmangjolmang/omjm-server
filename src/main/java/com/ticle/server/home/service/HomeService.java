package com.ticle.server.home.service;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.dto.request.SubscriptionRequest;
import com.ticle.server.home.dto.response.HomeResponse;
import com.ticle.server.home.dto.response.PostSetsResponse;
import com.ticle.server.home.repository.SubscriptionRepository;
import com.ticle.server.post.domain.Post;
import com.ticle.server.post.repository.PostRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.exception.UserNotFoundException;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

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
        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        Subscription subscription = request.toSubscription(user);
        subscriptionRepository.save(subscription);
    }

    public List<HomeResponse> getPostsByTopic() {
        // 이번주 TOP 3
        List<HomeResponse> responseList = getTop3Posts();
        // 랜덤 3개의 주제와 포스트
        getRandom3TopicAndPosts(responseList);

        return responseList;
    }

    private void getRandom3TopicAndPosts(List<HomeResponse> responseList) {
        Map<String, List<Post>> randomPosts = postTopicCache.getRandomPosts(3);

        for (Map.Entry<String, List<Post>> entry : randomPosts.entrySet()) {
            String commonTitle = entry.getKey();
            List<Post> posts = entry.getValue();

            List<PostSetsResponse> postResponses = posts.stream()
                    .map(PostSetsResponse::of)
                    .toList();

            responseList.add(HomeResponse.of(commonTitle, postResponses));
        }
    }

    private List<HomeResponse> getTop3Posts() {
        List<HomeResponse> responseList = new ArrayList<>();

        List<Post> topPosts = postRepository.findTop3ByOrderByScrapCountDesc();
        List<PostSetsResponse> responses = topPosts.stream()
                .map(PostSetsResponse::of)
                .toList();

        responseList.add(HomeResponse.of("이번주 TOP 3", responses));
        return responseList;
    }
}
