package com.hmdandelion.project_1410002.employee.domain.repository;

import com.hmdandelion.project_1410002.employee.domain.entity.QDepartment;
import com.hmdandelion.project_1410002.employee.domain.entity.QPosition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeRepoCustomImpl implements EmployeeRepoCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public String getDepartmentName(Long departmentCode) {
        QDepartment department = QDepartment.department;

        return queryFactory
                .select(department.departmentName)
                .from(department)
                .where(department.departmentCode.eq(departmentCode))
                .fetchOne();
    }

    @Override
    public String getPositonName(Long positionCode) {
        QPosition position = QPosition.position;
        return queryFactory
                .select(position.positionName)
                .from(position)
                .where(position.positionCode.eq(positionCode))
                .fetchOne();
    }
}
