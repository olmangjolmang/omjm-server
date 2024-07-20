package com.ticle.server.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;


public record ProfileUpdateRequest(Optional<String> nickName, Optional<String> email) {

}
