package com.hmdandelion.project_1410002.production.domain.repository.material;

import com.hmdandelion.project_1410002.production.domain.entity.QWorkOrder;
import com.hmdandelion.project_1410002.production.domain.entity.line.QLine;
import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.domain.entity.material.QStockUsage;
import com.hmdandelion.project_1410002.production.domain.entity.material.StockUsage;
import com.hmdandelion.project_1410002.production.domain.type.MaterialUsageStatus;
import com.hmdandelion.project_1410002.production.dto.material.MaterialUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.StockUsageDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec.materialSpec;
import static com.hmdandelion.project_1410002.production.domain.entity.material.QMaterialUsage.materialUsage;
import static com.hmdandelion.project_1410002.production.domain.entity.material.QStockUsage.stockUsage;

@RequiredArgsConstructor
public class MaterialUsageRepoCustomImpl implements MaterialUsageRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MaterialUsageDTO> searchUse(Pageable pageable, List<Long> stockCodes, String sortType) {
        BooleanBuilder builder = new BooleanBuilder();
        List<Long> targetCodes;
        if (!stockCodes.isEmpty()) {
            //재고-사용 리스트 도출
            targetCodes = queryFactory
                    .select(stockUsage.usageCode)
                    .from(stockUsage)
                    .where(stockUsage.stockCode.in(stockCodes))
                    .fetch();
            builder.and(materialUsage.usageCode.in(targetCodes));
        }
        if (sortType.equals("not_complete")) {
            builder.and(materialUsage.status.ne(MaterialUsageStatus.COMPLETED));
        } else {
            builder.and(materialUsage.status.eq(MaterialUsageStatus.COMPLETED));
        }

        List<MaterialUsage> usages = queryFactory
                .select(materialUsage)
                .from(materialUsage)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return usages.stream().map(MaterialUsageDTO::from).toList();
    }
}
