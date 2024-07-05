package com.ticle.server.talk.domain.type;

import org.springframework.data.domain.Sort;

public enum Order {
    TIME,
    HEART,
    ;

    public static Sort getOrder(Order orderBy) {
        return switch (orderBy) {
            case TIME -> Sort.by(Sort.Order.desc("createdDate"));
            case HEART -> Sort.by(Sort.Order.desc("heartCount"));
        };
    }
}
