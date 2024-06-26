package com.hmdandelion.project_1410002.inventory.dto.material.response;

import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialGraphModel;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.CombinedStockBySpecDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MaterialGraphResponse {

    public final List<MaterialGraphModel> data;

    public static MaterialGraphResponse from(List<MaterialGraphModel> list) {
        return new MaterialGraphResponse(list);
    }
}
