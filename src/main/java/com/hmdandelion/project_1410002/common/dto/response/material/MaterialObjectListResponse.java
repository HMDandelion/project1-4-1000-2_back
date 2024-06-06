package com.hmdandelion.project_1410002.common.dto.response.material;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialObjectListResponse {

    private final List<Object> list;

    public static MaterialObjectListResponse from(List<Object> materialList) {
        return new MaterialObjectListResponse(
                materialList
        );
    }
}
