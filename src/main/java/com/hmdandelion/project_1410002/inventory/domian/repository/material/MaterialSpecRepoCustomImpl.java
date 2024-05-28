package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MaterialSpecRepoCustomImpl implements MaterialSpecRepoCustom {

    private static final Logger log = LoggerFactory.getLogger(MaterialSpecRepoCustomImpl.class);
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MaterialSpec> searchMaterialSpec(final Pageable pageable, final String materialName) {
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;
        BooleanBuilder builder = new BooleanBuilder();

        if (materialName != null && !materialName.isEmpty()) {
            log.info("검색값 확인됨...{}", materialName);
            builder.and(materialSpec.materialName.containsIgnoreCase(materialName));
        }

        return queryFactory
                .selectFrom(materialSpec)
                .where(builder)
                .orderBy(materialSpec.specCode.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public long removeByList(List<Long> specCodes) {
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;
        return queryFactory
                .delete(materialSpec)
                .where(materialSpec.specCode.in(specCodes))
                .execute();
    }
}
