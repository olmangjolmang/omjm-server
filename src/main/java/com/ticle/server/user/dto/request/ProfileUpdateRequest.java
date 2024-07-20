package com.ticle.server.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class ProfileUpdateRequest {

    private Optional<String> nickName = Optional.empty();
    private Optional<String> email = Optional.empty();

}
