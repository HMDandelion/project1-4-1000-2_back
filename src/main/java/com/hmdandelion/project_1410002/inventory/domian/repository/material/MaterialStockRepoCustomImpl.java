package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.QWarehouse;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Warehouse;
import com.hmdandelion.project_1410002.inventory.domian.type.StockDivision;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockDetailDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialStockSimpleDTO;
import com.hmdandelion.project_1410002.inventory.dto.material.request.SaveMaterialStockRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class MaterialStockRepoCustomImpl implements MaterialStockRepoCustom {

    private static final Logger log = LoggerFactory.getLogger(MaterialStockRepoCustomImpl.class);
    private final MaterialSpecRepo materialSpecRepo;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MaterialStock> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode) {
        QMaterialStock materialStock = QMaterialStock.materialStock;
        BooleanBuilder builder = new BooleanBuilder();

        if (materialName != null) {
            builder.and(materialStock.materialSpec.materialName.contains(materialName));
        }
        if (warehouseCode != null) {
            builder.and(materialStock.warehouse.warehouseCode.eq(warehouseCode));
        } else {
            builder.and(materialStock.materialSpec.category.categoryCode.eq(specCategoryCode));
        }

        List<MaterialStock> stocks = queryFactory
                .selectFrom(materialStock)
                .where(builder)
                .where(materialStock.actualQuantity.gt(0))
                .orderBy(materialStock.stockCode.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return stocks;
    }

    @Override
    public MaterialStock getStockByCode(Long stockCode) {
        QMaterialStock materialStock = QMaterialStock.materialStock;
        return queryFactory
                .selectFrom(materialStock)
                .where(materialStock.actualQuantity.gt(0))
                .where(materialStock.stockCode.eq(stockCode))
                .fetchFirst();
    }

}
