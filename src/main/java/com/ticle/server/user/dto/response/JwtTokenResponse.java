package com.ticle.server.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
