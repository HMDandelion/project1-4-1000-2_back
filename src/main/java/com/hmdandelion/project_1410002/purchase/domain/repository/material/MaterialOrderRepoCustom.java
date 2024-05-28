package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;

import java.util.List;

public interface MaterialOrderRepoCustom {

    List<MaterialOrderDTO> getSearch(Long SpecCode, int year, int Month);
}
