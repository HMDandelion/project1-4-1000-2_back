package com.hmdandelion.project_1410002.purchase.dto.material.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class MaterialOrderWeeklyResponse {

    private final Map<String, Long> orderThisWeek;

    public static MaterialOrderWeeklyResponse from(Map<String, Long> orderThisWeek) {
        return new MaterialOrderWeeklyResponse(orderThisWeek);
    }
}
