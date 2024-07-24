package com.ticle.server.user.exception;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenNotValidationException ex) {
            setErrorResponses(HttpStatus.UNAUTHORIZED, request, response, ex);
        }
    }

    public void setErrorResponses(HttpStatus status, HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json;charset=UTF-8");

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String strNowDate = simpleDateFormat.format(nowDate);

        response.getWriter().write(
                new JwtErrorResponse(
                        strNowDate,
                        HttpStatus.UNAUTHORIZED.toString(),
                        "Unauthorized",
                        ex.getMessage(),
                        request.getRequestURI()
                ).convertToJson()
        );
    }
}