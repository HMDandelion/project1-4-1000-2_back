package com.hmdandelion.project_1410002.inventory.dto.material.response;

import com.hmdandelion.project_1410002.inventory.domian.repository.material.MaterialStockRepo;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialStockSimpleDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialStockRes {
    private final List<MaterialStockSimpleDTO> list;

    public static MaterialStockRes of(List<MaterialStockSimpleDTO> list) {
        return new MaterialStockRes(list);
    }
}
