package com.ticle.server.user.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.opinion.domain.Opinion;
import com.ticle.server.user.domain.User;
import com.ticle.server.user.domain.type.Category;
import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Order(1)
@LocalDummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() > 0) {
            log.info("[User] 더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            User user1 = User.builder()
                    .email("user1@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user1")
                    .category(Arrays.asList(Category.BACKEND,Category.INFRA))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user2 = User.builder()
                    .email("user2@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user2")
                    .category(Arrays.asList(Category.WEB_FRONT))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user3 = User.builder()
                    .email("user3@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user3")
                    .category(Arrays.asList(Category.NETWORK))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user4 = User.builder()
                    .email("user4@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user4")
                    .category(Arrays.asList(Category.APP))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user5 = User.builder()
                    .email("user5@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user5")
                    .category(Arrays.asList(Category.BACKEND))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user6 = User.builder()
                    .email("user6@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user6")
                    .category(Arrays.asList(Category.VISION))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user7 = User.builder()
                    .email("user7@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user7")
                    .category(Arrays.asList(Category.AI,Category.INFRA))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user8 = User.builder()
                    .email("user8@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user8")
                    .category(Arrays.asList(Category.APP))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user9 = User.builder()
                    .email("user9@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user9")
                    .category(Arrays.asList(Category.APP))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user10 = User.builder()
                    .email("user10@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user10")
                    .category(Arrays.asList(Category.WEB_FRONT))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user11 = User.builder()
                    .email("user11@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user11")
                    .category(Arrays.asList(Category.NETWORK))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user12 = User.builder()
                    .email("user12@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user12")
                    .category(Arrays.asList(Category.APP))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user13 = User.builder()
                    .email("user13@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user13")
                    .category(Arrays.asList(Category.INFRA,Category.AI))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            User user14 = User.builder()
                    .email("user14@example.com")
                    .password(passwordEncoder.encode("password2"))
                    .nickName("user14")
                    .category(Arrays.asList(Category.VISION,Category.ETC))
                    .agreeTerms(true)
                    .roles(Arrays.asList("USER"))
                    .build();

            userList.add(user1);
            userList.add(user2);
            userList.add(user3);
            userList.add(user4);
            userList.add(user5);
            userList.add(user6);
            userList.add(user7);
            userList.add(user8);
            userList.add(user9);
            userList.add(user10);
            userList.add(user11);
            userList.add(user12);
            userList.add(user13);
            userList.add(user14);

            userRepository.saveAll(userList);
        }
    }
}