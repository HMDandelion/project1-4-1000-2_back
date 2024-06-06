package com.hmdandelion.project_1410002.inventory.dto.material.response;


import com.hmdandelion.project_1410002.inventory.dto.material.CombinedStockBySpecDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialGraphModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialGraphRes {
    public final List<MaterialGraphModel> data;

    public static MaterialGraphRes from(List<CombinedStockBySpecDTO> stocks) {
        List<MaterialGraphModel> data = new ArrayList<>();
        for (CombinedStockBySpecDTO stock : stocks) {
            data.add(
                    new MaterialGraphModel(stock.getMaterialName(),
                                           stock.getActualQuantity(),
                                           stock.getSafetyStock())
            );
        }
        return new MaterialGraphRes(data);
    }
}
