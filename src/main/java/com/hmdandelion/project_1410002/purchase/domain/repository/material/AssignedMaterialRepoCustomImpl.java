package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.AssignedMaterial;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.QAssignedMaterial;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssignedMaterialRepoCustomImpl implements AssignedMaterialRepoCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAssignedByClientCode(Long clientCode)  {
        QAssignedMaterial assignedMaterial = QAssignedMaterial.assignedMaterial;

        AssignedMaterial temp = queryFactory
                .selectFrom(assignedMaterial)
                .where(assignedMaterial.clientCode.eq(clientCode))
                .fetchFirst();
        if (temp != null) {
            queryFactory
                    .delete(assignedMaterial)
                    .where(assignedMaterial.clientCode.eq(clientCode))
                    .execute();
        }

    }
}
