package com.hmdandelion.project_1410002.inventory.dto.material.response;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockDetailDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialStockRes {
    private final List<MaterialStockDTO> list;

    public static MaterialStockRes from(List<MaterialStockSimpleDTO> list) {
        return new MaterialStockRes(list.stream().map(item -> (MaterialStockDTO)item).toList());
    }
    public static MaterialStockRes from(MaterialStockDetailDTO detail) {
        List<MaterialStockDTO> list = new ArrayList<>();
        list.add(detail);
        return new MaterialStockRes(list);
    }
}
