package com.ticle.server.home.service;

import com.ticle.server.home.domain.Subscription;
import com.ticle.server.home.dto.request.SubscriptionRequest;
import com.ticle.server.home.dto.request.ValidEmailRequest;
import com.ticle.server.home.dto.request.ValidateNickNameRequest;
import com.ticle.server.home.repository.SubscriptionRepository;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public Boolean validateEmail(ValidEmailRequest request) {
        return userRepository.existsByEmail(request.email());
    }

    public Boolean validateNickName(ValidateNickNameRequest request) {
        return userRepository.existsByNickName(request.nickName());
    }

    @Transactional
    public void uploadSubscription(SubscriptionRequest request, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(RuntimeException::new);

        Subscription subscription = request.toSubscription(user);
        subscriptionRepository.save(subscription);
    }
}
