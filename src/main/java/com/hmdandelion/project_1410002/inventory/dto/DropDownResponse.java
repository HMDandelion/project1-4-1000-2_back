package com.hmdandelion.project_1410002.inventory.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DropDownResponse {
    private Long code;
    private String name;

    public static DropDownResponse of(Long code, String name) {
        return new DropDownResponse(code, name);
    }
}
