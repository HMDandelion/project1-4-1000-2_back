package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialStock;
import com.hmdandelion.project_1410002.inventory.dto.material.MaterialStockSimpleDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class MaterialStockRepoCustomImpl implements MaterialStockRepoCustom {
    public final JPAQueryFactory queryFactory;

    @Override
    public List<MaterialStockSimpleDTO> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode) {
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
                .orderBy(materialStock.stockCode.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return stocks.stream().map(MaterialStockSimpleDTO::from).toList();
    }
}
