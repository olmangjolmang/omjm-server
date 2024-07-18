package com.ticle.server.post.domain.type;

import com.ticle.server.opinion.domain.type.Order;
import org.springframework.data.domain.Sort;

public enum PostSort {
    LATEST, // 최신순
    OLDEST, // 오래된순
    SCRAPPED; // 스크랩순

    public static Sort getOrder(PostSort orderBy) {
        return switch (orderBy) {
            case LATEST -> Sort.by(Sort.Direction.DESC, "createdDate");
            case OLDEST -> Sort.by(Sort.Direction.ASC, "createdDate");
            case SCRAPPED -> Sort.by(Sort.Direction.DESC, "scrapCount");

        };
    }

}