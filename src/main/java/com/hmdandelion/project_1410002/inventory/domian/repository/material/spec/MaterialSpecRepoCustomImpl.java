package com.hmdandelion.project_1410002.inventory.domian.repository.material.spec;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialStock;
import com.hmdandelion.project_1410002.inventory.dto.material.dto.MaterialSpecDTO;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.AssignedMaterial;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.QAssignedMaterial;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MaterialSpecRepoCustomImpl implements MaterialSpecRepoCustom {

    private static final Logger log = LoggerFactory.getLogger(MaterialSpecRepoCustomImpl.class);
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MaterialSpec> searchMaterialSpec(final Pageable pageable, final String materialName) {
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;
        BooleanBuilder builder = new BooleanBuilder();

        if (materialName != null && !materialName.isEmpty()) {
            log.info("검색값 확인됨...{}", materialName);
            builder.and(materialSpec.materialName.containsIgnoreCase(materialName));
        }
        List<MaterialSpec> list = queryFactory
                .selectFrom(materialSpec)
                .where(builder)
                .orderBy(materialSpec.specCode.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(materialSpec.count())
                .from(materialSpec)
                .orderBy(materialSpec.specCode.asc());

        return PageableExecutionUtils.getPage(list,pageable, countQuery::fetchOne);
    }

    @Override
    public long removeByList(List<Long> specCodes) {
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;
        return queryFactory
                .delete(materialSpec)
                .where(materialSpec.specCode.in(specCodes))
                .execute();
    }

    @Override
    public Long getUsingSepcCode(List<Long> specCodes) {
        QMaterialStock materialStock = QMaterialStock.materialStock;

        return queryFactory
                .select(materialStock.count())
                .from(materialStock)
                .where(materialStock.materialSpec.specCode.in(specCodes))
                .fetchOne();
    }

    @Override
    public Map<Long, List<MaterialSpecDTO>> getSpecByClientCodes(List<Long> clientCodes) {
        QAssignedMaterial assignedMaterial = QAssignedMaterial.assignedMaterial;
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;

        List<Tuple> results = queryFactory
                .select(assignedMaterial.clientCode, materialSpec)
                .from(assignedMaterial)
                .join(materialSpec).on(assignedMaterial.specCode.eq(materialSpec.specCode))
                .where(assignedMaterial.clientCode.in(clientCodes))
                .fetch();

        Map<Long, List<MaterialSpecDTO>> resultMap = new HashMap<>();
        for (Tuple tuple : results) {
            Long clientCode = tuple.get(assignedMaterial.clientCode);
            MaterialSpecDTO specDTO = MaterialSpecDTO.from(tuple.get(materialSpec));
            resultMap.computeIfAbsent(clientCode, k -> new ArrayList<>()).add(specDTO);
        }

        return resultMap;
    }



}
