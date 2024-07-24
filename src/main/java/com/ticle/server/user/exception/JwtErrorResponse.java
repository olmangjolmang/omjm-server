package com.ticle.server.user.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticle.server.global.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
@RequiredArgsConstructor
public class JwtErrorResponse {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String timestamp;
    private final String status;
    private final String error;
    private final String message;
    private final String path;

    public String convertToJson() throws JsonProcessingException{
        return objectMapper.writeValueAsString(this);
    }
}
