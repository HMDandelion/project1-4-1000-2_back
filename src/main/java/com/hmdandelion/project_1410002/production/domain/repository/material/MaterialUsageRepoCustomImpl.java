package com.hmdandelion.project_1410002.production.domain.repository.material;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.employee.domain.entity.Employee;
import com.hmdandelion.project_1410002.employee.domain.entity.QDepartment;
import com.hmdandelion.project_1410002.employee.domain.entity.QPosition;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.QBom;
import com.hmdandelion.project_1410002.production.domain.entity.line.QLine;
import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.domain.type.MaterialUsageStatus;
import com.hmdandelion.project_1410002.production.dto.material.MaterialUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.response.MaterialUsageResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hmdandelion.project_1410002.employee.domain.entity.QEmployee.employee;
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

    @Override
    public MaterialUsageResponse getMaterialUsage(Long usageCode) {
        MaterialUsage usage = queryFactory
                .select(materialUsage)
                .from(materialUsage)
                .where(materialUsage.usageCode.eq(usageCode))
                .fetchOne();
        if (usage == null) {
            throw new NotFoundException(ExceptionCode.NOT_FOUND_USAGE_CODE);
        }
        String empName = "미배정";
        String phone = "";
        String positionName = "";
        String departmentName = "";
        if (usage.getEmployeeCode() != null) {

            Employee emp = queryFactory
                    .selectFrom(employee)
                    .where(employee.employeeCode.eq(usage.getEmployeeCode()))
                    .fetchOne();

            empName = emp.getEmployeeName();
            phone = emp.getPhone();
            QPosition position = QPosition.position;
            positionName = queryFactory
                    .select(position.positionName)
                    .from(position)
                    .where(position.positionCode.eq(emp.getPositionCode()))
                    .fetchOne();

            QDepartment department = QDepartment.department;
            departmentName = queryFactory
                    .select(department.departmentName)
                    .from(department)
                    .where(department.departmentCode.eq(emp.getDepartmentCode()))
                    .fetchOne();

        }
        QLine line = QLine.line;
        String lineName = queryFactory
                .select(line.lineName)
                .from(line)
                .where(line.lineCode.eq(usage.getWorkOrder().getLineCode()))
                .fetchOne();

        QBom bom = QBom.bom;
        List<Bom> boms = queryFactory
                .selectFrom(bom)
                .where(bom.product.productCode.eq(usage.getWorkOrder().getProductCode()))
                .fetch();


        return MaterialUsageResponse.of(usage,
                                        empName,
                                        positionName,
                                        departmentName,
                                        phone,
                                        lineName,
                                        boms);
    }
}
