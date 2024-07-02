package com.ticle.server.talk.repository.init;

import com.ticle.server.global.util.LocalDummyDataInit;
import com.ticle.server.talk.repository.TalkRepository;
import com.ticle.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor
@Order(2)
@LocalDummyDataInit
public class TalkInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final TalkRepository talkRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
