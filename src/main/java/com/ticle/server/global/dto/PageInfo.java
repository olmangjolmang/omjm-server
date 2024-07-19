package com.ticle.server.global.dto;

import org.springframework.data.domain.Page;

public record PageInfo(
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static PageInfo from(Page<?> page) {
        return new PageInfo(
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}
