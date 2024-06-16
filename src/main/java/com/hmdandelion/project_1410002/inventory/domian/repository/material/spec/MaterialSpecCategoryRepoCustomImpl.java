package com.hmdandelion.project_1410002.inventory.domian.repository.material.spec;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MaterialSpecCategoryRepoCustomImpl implements MaterialSpecCategoryRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isCanDelete(Long categoryCode) {
        QMaterialSpec spec = QMaterialSpec.materialSpec;
        List<MaterialSpec> list = queryFactory
                .select(spec)
                .from(spec)
                .where(spec.category.categoryCode.eq(categoryCode))
                .fetch();
        return list.isEmpty();
    }
}
