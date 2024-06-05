package com.hmdandelion.project_1410002.production.domain.repository.productionPlan;
import com.hmdandelion.project_1410002.production.dto.response.WorkOrderResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct.product;
import static com.hmdandelion.project_1410002.production.domain.entity.QWorkOrder.workOrder;
import static com.hmdandelion.project_1410002.production.domain.entity.line.QLine.line;
import static com.hmdandelion.project_1410002.employee.domain.entity.QEmployee.employee;

@RequiredArgsConstructor
public class WorkOrderRepoCustomImpl implements WorkOrderRepoCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<WorkOrderResponse> getWorkOrders(Pageable pageable) {
        /* workOrder query dsl 작성...해!!! 당장!!!!! */
        List<WorkOrderResponse> workOrders = queryFactory
                .select(Projections.constructor(WorkOrderResponse.class,
                        workOrder.workOrderCode,
                        workOrder.workOrderDate,
                        workOrder.workWrittenDate,
                        workOrder.orderedQuantity,
                        workOrder.completionStatus,
                        product.productName,
                        employee.employeeName,
                        line.lineName
                )).from(workOrder)
                .leftJoin(product).on(workOrder.productCode.eq(product.productCode))
                .leftJoin(employee).on(workOrder.employeeCode.eq(employee.employeeCode))
                .leftJoin(line).on(workOrder.lineCode.eq(line.lineCode))
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(workOrder.count())
                .from(workOrder)
                .leftJoin(product).on(workOrder.productCode.eq(product.productCode))
                .leftJoin(employee).on(workOrder.employeeCode.eq(employee.employeeCode))
                .leftJoin(line).on(workOrder.lineCode.eq(line.lineCode));

        return PageableExecutionUtils.getPage(workOrders, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<WorkOrderResponse> getWorkOrder(Long workOrderCode) {
        //사용안함
        return Optional.empty();
    }
}
