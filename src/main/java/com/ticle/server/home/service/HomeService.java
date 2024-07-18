package com.ticle.server.home.service;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.dto.request.SubscriptionRequest;
import com.ticle.server.home.repository.SubscriptionRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.exception.UserNotFoundException;
import com.ticle.server.user.repository.UserRepository;
import com.ticle.server.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ticle.server.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void uploadSubscription(SubscriptionRequest request, CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        Subscription subscription = request.toSubscription(user);
        subscriptionRepository.save(subscription);
    }
}
