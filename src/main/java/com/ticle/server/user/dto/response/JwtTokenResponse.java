package com.ticle.server.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record JwtTokenResponse(String grantType,String accessToken,String refreshToken) {

}
