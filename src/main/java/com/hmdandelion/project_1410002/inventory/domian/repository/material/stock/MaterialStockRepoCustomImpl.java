package com.hmdandelion.project_1410002.inventory.domian.repository.material.stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialStock;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.spec.MaterialSpecRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.material.stock.MaterialStockRepoCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class MaterialStockRepoCustomImpl implements MaterialStockRepoCustom {

    private static final Logger log = LoggerFactory.getLogger(MaterialStockRepoCustomImpl.class);
    private final MaterialSpecRepo materialSpecRepo;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MaterialStock> searchMaterialStock(Pageable pageable, String materialName, Long warehouseCode, Long specCategoryCode) {
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
        JPAQuery<Long> count = queryFactory
                .select(materialStock.count())
                .from(materialStock)
                .where(builder)
                .where(materialStock.actualQuantity.gt(0))
                .orderBy(materialStock.stockCode.desc());
        return PageableExecutionUtils.getPage(stocks, pageable, count::fetchOne);
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

    @Override
    public List<Long> searchMaterialStockByMaterialName(String materialName) {
        QMaterialStock materialStock = QMaterialStock.materialStock;
        return queryFactory.select(materialStock.stockCode)
                           .from(materialStock)
                           .where(materialStock.materialSpec.materialName.contains(materialName))
                           .fetch();

    }

}
