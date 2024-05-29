package com.hmdandelion.project_1410002.inventory.dto.material.response;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialSpecRes {
    private final List<MaterialSpecDTO> specList;

    public static MaterialSpecRes from(List<MaterialSpecDTO> specList) {
        return new MaterialSpecRes(specList);
    }
}
