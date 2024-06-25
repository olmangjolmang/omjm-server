package com.ticle.server.global.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder
public record ErrorResponse(
        Boolean isSuccess,
        String code,
        String message,
        @JsonInclude(Include.NON_EMPTY)
        ValidationErrors results
) {

    public record ValidationErrors(
            List<ValidationError> validationErrors
    ) {

    }

    //@Valid 오류의 경우 사용 - error 발생 이유
    @Builder
    public record ValidationError(
            String field,
            String message
    ) {
        public static ValidationError from(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}