package com.ticle.server.home.domain.type;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Day {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");

    private final String dayOfWeek;

    private static final Map<String, Day> BY_DAY_OF_WEEK = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Day::getDayOfWeek, Function.identity())));

    public static Day getDayOfWeek(String dayOfWeek) {
        return BY_DAY_OF_WEEK.get(dayOfWeek);
    }

    Day(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}