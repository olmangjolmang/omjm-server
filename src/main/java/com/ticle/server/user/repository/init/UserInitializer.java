package com.ticle.server.user.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
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
@Order(1)
@LocalDummyDataInit
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*if (userRepository.count() > 0) {
            log.info("[User]더미 데이터 존재");
        } else {
            List<User> userList = new ArrayList<>();

            for (int i = 0; i < 1; i++) {
                User DUMMY_USER = User.builder()
                        .email()
                        .build();
                User DUMMY_ADMIN = User.builder()
                        .email("email5")
                        .name("adminName")
                        .socialId("socialId2")
                        .gender(Gender.MAN)
                        .role(Role.ADMIN)
                        .score(3L)
                        .build();

                UserList.add(DUMMY_USER);
                UserList.add(DUMMY_ADMIN);
                UserList.add(User.builder()
                        .email("email2")
                        .name("name2")
                        .gender(Gender.WOMAN)
                        .socialId("socialId3")
                        .role(Role.User)
                        .score(2L)
                        .build());
                UserList.add(User.builder()
                        .email("email3")
                        .name("name3")
                        .gender(Gender.MAN)
                        .role(Role.User)
                        .socialId("socialId4")
                        .score(6L)
                        .build());
                UserList.add(User.builder()
                        .email("email4")
                        .name("name4")
                        .gender(Gender.MAN)
                        .role(Role.GUEST)
                        .socialId("socialId5")
                        .score(12L)
                        .build());
            }

            UserRepository.saveAll(UserList);
        }*/
    }
}
