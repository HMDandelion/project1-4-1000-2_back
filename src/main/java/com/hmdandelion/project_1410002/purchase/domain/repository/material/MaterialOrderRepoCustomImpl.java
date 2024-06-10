package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialSpec;
import com.hmdandelion.project_1410002.purchase.domain.entity.material.*;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import com.hmdandelion.project_1410002.purchase.dto.material.OrderSpecCreateDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hmdandelion.project_1410002.inventory.domian.entity.material.QMaterialSpec.materialSpec;
import static com.hmdandelion.project_1410002.purchase.domain.entity.material.QAssignedMaterial.assignedMaterial;
import static com.hmdandelion.project_1410002.purchase.domain.entity.material.QMaterialOrder.materialOrder;
import static com.hmdandelion.project_1410002.purchase.domain.entity.material.QOrderSpec.orderSpec;


@RequiredArgsConstructor
public class MaterialOrderRepoCustomImpl implements MaterialOrderRepoCustom {

    private static final Logger log = LoggerFactory.getLogger(MaterialOrderRepoCustomImpl.class);
    private final JPAQueryFactory queryFactory;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<MaterialOrderDTO> findMaterialOrderBySpecCodeAndYearMonth(Long specCode, int year, int month) {

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

        return queryFactory
                .selectFrom(orderSpec)
                .where(orderSpec.orderCode.eq(orderCode))
                .fetch();
    }

    @Override
    public Page<MaterialOrderDTO> gerOrders(Long planCode, String clientName, Pageable pageable) {
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
                .where(materialOrder.arrivalDatetime.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(materialOrder.orderDate.desc())
                .fetch();

        return getMaterialOrderDTOS(orders,pageable);
    }

    @Override
    public List<Long> findClientCodeBySpecCodes(List<Long> specCodes) {

        return queryFactory
                .select(assignedMaterial.clientCode)
                .from(assignedMaterial)
                .where(assignedMaterial.specCode.in(specCodes))
                .fetch();
    }

    @Override
    public void setOrderSpec(Long orderCode, List<OrderSpecCreateDTO> orderSpecList) {

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

        queryFactory.delete(orderSpec)
                    .where(orderSpec.orderCode.eq(orderCode))
                    .execute();
    }

    @Override
    public Page<MaterialOrderDTO> getOrderToday(Pageable pageable, LocalDate today) {
        List<MaterialOrder> orders = queryFactory
                .selectFrom(materialOrder)
                .where(materialOrder.deliveryDueDate.eq(today))
                .fetch();
        return getMaterialOrderDTOS(orders,pageable);
    }

    @Override
    public Map<String, Long> orderWeekly(LocalDate targetDate) {
        Map<String, Long> orderThisWeek = new LinkedHashMap<>();
        String[] dayOfWeekNames = {"월요일", "화요일","수요일","목요일","금요일","토요일","일요일"};
        for (int i = 0; i < dayOfWeekNames.length; i++) {
            String dayOfWeekName = dayOfWeekNames[i];
            Long cal = queryFactory
                    .select(materialOrder.count())
                    .from(materialOrder)
                    .where(materialOrder.deliveryDueDate.eq(targetDate))
                    .fetchOne();
            if (cal == null) cal = 0L;
            orderThisWeek.put(dayOfWeekName, cal);

            targetDate = targetDate.plusDays(1);
        }

        return orderThisWeek;
    }

    private Page<MaterialOrderDTO> getMaterialOrderDTOS(List<MaterialOrder> orders,Pageable pageable) {
        List<OrderSpec> orderSpecs = queryFactory
                .selectFrom(orderSpec)
                .leftJoin(orderSpec.materialSpec).fetchJoin()
                .where(orderSpec.orderCode.in(orders.stream().map(MaterialOrder::getOrderCode).collect(Collectors.toList())))
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(orderSpec.count())
                .from(orderSpec)
                .where(orderSpec.orderCode.in(orders.stream().map(MaterialOrder::getOrderCode).collect(Collectors.toList())));

        Map<Long, List<OrderSpec>> specsMap = orderSpecs.stream()
                                                        .collect(Collectors.groupingBy(OrderSpec::getOrderCode));
        List<MaterialOrderDTO> list = orders.stream().map(order -> MaterialOrderDTO.of(order, specsMap)).toList();

        return PageableExecutionUtils.getPage(list, pageable, count::fetchOne);
    }
}
