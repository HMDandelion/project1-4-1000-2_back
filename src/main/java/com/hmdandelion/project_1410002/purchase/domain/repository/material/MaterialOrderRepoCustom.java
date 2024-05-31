package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;

import java.util.List;

public interface MaterialOrderRepoCustom {

    List<MaterialOrderDTO> findMaterialOrderBySpecCodeAndYearMonth(Long specCode, int year, int month);

    List<MaterialOrderDTO> getLast10OrderBySpecCode(long specCode);

    List<OrderSpec> getOrderSpecsByOrderCode(Long orderCode);
}
