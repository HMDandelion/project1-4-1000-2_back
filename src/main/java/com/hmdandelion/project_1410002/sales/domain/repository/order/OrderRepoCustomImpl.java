package com.hmdandelion.project_1410002.sales.domain.repository.order;

import com.hmdandelion.project_1410002.sales.dto.response.*;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct.product;
import static com.hmdandelion.project_1410002.inventory.domian.entity.stock.QStock.stock;
import static com.hmdandelion.project_1410002.inventory.domian.entity.stock.QStorage.storage;
import static com.hmdandelion.project_1410002.sales.domain.entity.estimate.QEstimate.estimate;
import static com.hmdandelion.project_1410002.sales.domain.entity.order.QOrder.order;
import static com.hmdandelion.project_1410002.sales.domain.entity.client.QClient.client;
import static com.hmdandelion.project_1410002.sales.domain.entity.order.QOrderProduct.orderProduct;

@RequiredArgsConstructor
public class OrderRepoCustomImpl implements OrderRepoCustom{
    private final JPAQueryFactory queryFactory;

    private OrderSpecifier createOrderSpecifier(String sort) {
        return switch (sort != null ? sort : "none") {
            case "orderDate" -> new OrderSpecifier<>(Order.ASC, order.orderDatetime);
            case "-orderDate" -> new OrderSpecifier<>(Order.DESC, order.orderDatetime);
            case "deadline" -> new OrderSpecifier<>(Order.ASC, order.deadline);
            case "-deadline" -> new OrderSpecifier<>(Order.DESC, order.deadline);
            default -> new OrderSpecifier<>(Order.ASC, order.orderCode);
        };
    }

    @Override
    public Page<OrdersResponse> search (
            Pageable pageable, LocalDate startDate, LocalDate endDate,
            String clientName, String status, String productName, String sort
    ) {
        OrderSpecifier orderSpecifier = createOrderSpecifier(sort);

        List<OrdersResponse> orders = queryFactory
                .select(Projections.constructor(OrdersResponse.class,
                        order.orderCode,
                        order.orderDatetime,
                        client.clientName,
                        order.status,
                        order.deadline
                ))
                .from(order)
                .leftJoin(client).on(order.clientCode.eq(client.clientCode))
                .leftJoin(order.orderProducts, orderProduct)
                .leftJoin(product).on(orderProduct.productCode.eq(product.productCode))
                .where(
                        containClientName(clientName),
                        containProductName(productName),
                        searchDateFilter(startDate, endDate)
                )
                .groupBy(order.orderCode)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(order.orderCode.countDistinct())
                .from(order)
                .leftJoin(client).on(order.clientCode.eq(client.clientCode))
                .leftJoin(order.orderProducts, orderProduct)
                .leftJoin(product).on(orderProduct.productCode.eq(product.productCode))
                .where(
                        containClientName(clientName),
                        containProductName(productName),
                        searchDateFilter(startDate, endDate)
                )
                .orderBy(orderSpecifier);

        return PageableExecutionUtils.getPage(orders, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<OrderResponse> getOrder(Long orderCode) {
        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);

        Map<Long, OrderResponse> result = queryFactory
                .from(order)
                .leftJoin(client).on(order.clientCode.eq(client.clientCode))
                .leftJoin(order.orderProducts, orderProduct)
                .leftJoin(product).on(orderProduct.productCode.eq(product.productCode))
                .where(
                        order.orderCode.eq(orderCode)
                )
                .transform(
                        GroupBy.groupBy(order.orderCode).as(
                                Projections.constructor(OrderResponse.class,
                                        order.orderCode,
                                        order.orderDatetime,
                                        order.deadline,
                                        order.completedAt,
                                        order.status,
                                        Projections.constructor(OrderClientDTO.class,
                                                client.clientCode,
                                                client.clientName,
                                                client.representativeName,
                                                client.address,
                                                client.addressDetail,
                                                client.phone
                                        ),
                                        GroupBy.list(Projections.constructor(OrderProductResponse.class,
                                                product.productCode,
                                                product.productName,
                                                orderProduct.quantity,
                                                orderProduct.price
                                        ))
                                )
                        )
                );

        return Optional.ofNullable(result.get(orderCode));
    }

    /* 나윤 */
    @Override
    public Page<PlanningOrderResponse> getPlanningOrders(Pageable pageable, LocalDate startDate, LocalDate endDate, String clientName, String status, String productName, String sort) {
        Map<Long, PlanningOrderResponse> planningOrderMap = queryFactory
                .from(order)
                .leftJoin(client).on(order.clientCode.eq(client.clientCode))
                .leftJoin(order.orderProducts, orderProduct)
                .leftJoin(product).on(orderProduct.productCode.eq(product.productCode))
                .where(
                        containClientName(clientName),
                        containProductName(productName),
                        searchDateFilter(startDate, endDate)
                )
                .transform(GroupBy.groupBy(order.orderCode).as(
                        Projections.constructor(PlanningOrderResponse.class,
                                order.orderCode,
                                order.orderDatetime,
                                order.deadline,
                                order.completedAt,
                                client.clientName,
                                order.status,
                                GroupBy.list(Projections.constructor(OrderProductResponse.class,
                                        product.productCode,
                                        product.productName,
                                        orderProduct.quantity,
                                        orderProduct.price
                                ))
                        )
                ));

        List<PlanningOrderResponse> planningOrders = new ArrayList<>(planningOrderMap.values());

        // Count 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(order.orderCode.countDistinct())
                .from(order)
                .leftJoin(client).on(order.clientCode.eq(client.clientCode))
                .leftJoin(order.orderProducts, orderProduct)
                .leftJoin(product).on(orderProduct.productCode.eq(product.productCode))
                .where(
                        containClientName(clientName),
                        containProductName(productName),
                        searchDateFilter(startDate, endDate)
                );

        return PageableExecutionUtils.getPage(planningOrders, pageable, countQuery::fetchOne);
    }






    private BooleanExpression containClientName(String clientName) {
        if (clientName == null || clientName.isEmpty()) {
            return null;
        }
        return client.clientName.containsIgnoreCase(clientName);
    }

    private BooleanExpression containProductName(String productName) {
        if (productName == null || productName.isEmpty()) {
            return null;
        }
        return product.productName.containsIgnoreCase(productName);
    }

    private BooleanExpression searchDateFilter(LocalDate startDate, LocalDate endDate) {
        BooleanExpression isGoeStartDate = order.orderDatetime.goe(LocalDateTime.of(startDate, LocalTime.MIN));
        BooleanExpression isLoeEndDate = order.orderDatetime.loe(LocalDateTime.of(endDate, LocalTime.MAX).withNano(0));

        return Expressions.allOf(isGoeStartDate, isLoeEndDate);
    }
}
