package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.QMaterialOrder;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.QOrderSpec;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class MaterialOrderRepoCustomImpl implements MaterialOrderRepoCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<MaterialOrderDTO> findMaterialOrderBySpecCodeAndYearMonth(Long specCode, int year, int month) {
        QMaterialOrder materialOrder = QMaterialOrder.materialOrder;
        QOrderSpec orderSpec = QOrderSpec.orderSpec;
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        //오더-스펙에서 스펙이 존재하는 오더-스펙 코드만 분리
        List<Long> orderCodesWithSpec = queryFactory
                .select(orderSpec.orderCode)
                .from(orderSpec)
                .where(orderSpec.materialSpec.specCode.eq(specCode))
                .fetch();
        //분리한 코드 중에서 해당 기간내에 작성된 주문만 조회
        List<MaterialOrder> orders = queryFactory
                .selectFrom(materialOrder)
                .where(materialOrder.orderCode.in(orderCodesWithSpec)
                                              .and(materialOrder.orderDate.between(startDate, endDate)))
                .fetch();

        // 조회한 주문의 오더-스펙을 조회
        Map<Long, List<OrderSpec>> specsMap = queryFactory
                .selectFrom(orderSpec)
                .leftJoin(orderSpec.materialSpec, materialSpec)
                .where(orderSpec.orderCode.in(
                        orders.stream().map(MaterialOrder::getOrderCode).collect(Collectors.toSet())))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(OrderSpec::getOrderCode));
        // dto에 담아 전송
        return orders.stream().map(order -> MaterialOrderDTO.of(order, specsMap)).toList();
    }

    @Override
    public List<MaterialOrderDTO> getLast10OrderBySpecCode(long specCode) {
        QMaterialOrder materialOrder = QMaterialOrder.materialOrder;
        QOrderSpec orderSpec = QOrderSpec.orderSpec;
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;

        List<Long> orderCodesWithSpec = queryFactory
                .select(orderSpec.orderCode)
                .from(orderSpec)
                .where(orderSpec.materialSpec.specCode.eq(specCode))
                .fetch();
        List<MaterialOrder> orders = queryFactory
                .selectFrom(materialOrder)
                .where(materialOrder.orderCode.in(orderCodesWithSpec))
                .orderBy(materialOrder.orderDate.desc())
                .limit(10).stream().toList();
        Map<Long, List<OrderSpec>> specsMap = queryFactory
                .selectFrom(orderSpec)
                .leftJoin(orderSpec.materialSpec, materialSpec)
                .where(orderSpec.orderCode.in(
                        orders.stream().map(MaterialOrder::getOrderCode).collect(Collectors.toSet())))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(OrderSpec::getOrderCode));
        return orders.stream().map(order -> MaterialOrderDTO.of(order, specsMap)).toList();
    }
}
