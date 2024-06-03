package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.*;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecCreateDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class MaterialOrderRepoCustomImpl implements MaterialOrderRepoCustom {

    private static final Logger log = LoggerFactory.getLogger(MaterialOrderRepoCustomImpl.class);
    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;


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

    @Override
    public List<OrderSpec> getOrderSpecsByOrderCode(Long orderCode) {
        QOrderSpec orderSpec = QOrderSpec.orderSpec;

        return queryFactory
                .selectFrom(orderSpec)
                .where(orderSpec.orderCode.eq(orderCode))
                .fetch();
    }

    @Override
    public List<MaterialOrderDTO> gerOrders(Long planCode, String clientName, Pageable pageable) {
        QMaterialOrder materialOrder = QMaterialOrder.materialOrder;
        QOrderSpec orderSpec = QOrderSpec.orderSpec;
        BooleanBuilder builder = new BooleanBuilder();

        if (planCode != null && planCode > 0) {
            builder.and(materialOrder.planCode.eq(planCode));
        }
        if (clientName != null && !clientName.isBlank()) {
            builder.and(materialOrder.client.clientName.contains(clientName));
        }

        List<MaterialOrder> orders = queryFactory
                .selectFrom(materialOrder)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(materialOrder.orderDate.desc())
                .fetch();
        log.info("검색된 주문건의 수...{}",orders.size());
        for (MaterialOrder bb : orders) {
            log.info("주문번호...{}",bb.getOrderCode());
        }
        List<OrderSpec> orderSpecs = queryFactory
                .selectFrom(orderSpec)
                .leftJoin(orderSpec.materialSpec).fetchJoin() // 이 부분이 수정된 부분입니다.
                .where(orderSpec.orderCode.in(orders.stream().map(MaterialOrder::getOrderCode).collect(Collectors.toList())))
                .fetch();

        for (OrderSpec ccc : orderSpecs) {
            System.out.println(ccc.getOrderSpecCode());
            System.out.println(ccc.getMaterialSpec().getMaterialName());
        }

        Map<Long, List<OrderSpec>> specsMap = orderSpecs.stream()
                                                        .collect(Collectors.groupingBy(OrderSpec::getOrderCode));


        log.info("연결된 specsMap의 수...{}",specsMap.size());
        for (Map.Entry<Long, List<OrderSpec>> entry : specsMap.entrySet()) {
            System.out.println("=========================");
            System.out.println("key : " + entry.getKey());
            for (OrderSpec aaaa : entry.getValue()) {
                System.out.println(aaaa.getMaterialSpec().getMaterialName());
            }
            System.out.println("=========================");
        }
        return orders.stream().map(order -> MaterialOrderDTO.of(order, specsMap)).toList();
    }

    @Override
    public List<Long> findClientCodeBySpecCodes(List<Long> specCodes) {
        QAssignedMaterial assignedMaterial = QAssignedMaterial.assignedMaterial;

        return queryFactory
                .select(assignedMaterial.clientCode)
                .from(assignedMaterial)
                .where(assignedMaterial.specCode.in(specCodes))
                .fetch();
    }

    @Override
    public void setOrderSpec(Long orderCode, List<OrderSpecCreateDTO> orderSpecList) {
        QOrderSpec orderSpec = QOrderSpec.orderSpec;
        QMaterialSpec materialSpec = QMaterialSpec.materialSpec;

        List<Long> specCodes = orderSpecList.stream()
                .map(OrderSpecCreateDTO::getSpecCode)
                .toList();

        List<MaterialSpec> specs = queryFactory
                .selectFrom(materialSpec)
                .where(materialSpec.specCode.in(specCodes))
                .orderBy(materialSpec.specCode.asc())
                .fetch();

        Map<Long, MaterialSpec> specMap = specs.stream()
                                               .collect(Collectors.toMap(MaterialSpec::getSpecCode, spec -> spec));

        for (OrderSpecCreateDTO dto : orderSpecList) {
            MaterialSpec spec = specMap.get(dto.getSpecCode());

            OrderSpec newOrderSpec = OrderSpec.of(
                    orderCode,
                    spec,
                    dto.getOrderQuantity(),
                    dto.getPrice()
            );
            entityManager.persist(newOrderSpec);
        }
    }

    @Override
    public void deleteAllOrderSpecByOrderCode(Long orderCode) {
        QOrderSpec orderSpec = QOrderSpec.orderSpec;

        queryFactory.delete(orderSpec)
                    .where(orderSpec.orderCode.eq(orderCode))
                    .execute();
    }
}
