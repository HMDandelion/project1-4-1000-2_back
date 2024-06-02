package com.hmdandelion.project_1410002.production.domain.repository.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;

import java.util.Optional;

public interface ProductionDetailRepo {

    /* 생산 보고서 상세 조회*/
    Optional<ProductionManagement> findByProductionStatusCode(Long productionStatusCode);


    void save(ProductionDetail newProductionDetail);
}
