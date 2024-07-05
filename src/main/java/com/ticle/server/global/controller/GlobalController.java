package com.ticle.server.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "GlobalController", description = "전체 설정을 위해서 필요한 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/global")
public class GlobalController {

    @Value("${server.env}")
    private String env;

    @Operation(summary = "health check", description = "cicd를 위한 health check api")
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(env);
    }
}