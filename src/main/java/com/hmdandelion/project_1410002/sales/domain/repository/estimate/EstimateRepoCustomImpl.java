package com.hmdandelion.project_1410002.sales.domain.repository.estimate;

import com.hmdandelion.project_1410002.sales.dto.response.EstimateProductResponse;
import com.hmdandelion.project_1410002.sales.dto.response.EstimateResponse;
import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct.product;
import static com.hmdandelion.project_1410002.sales.domain.entity.client.QClient.client;
import static com.hmdandelion.project_1410002.sales.domain.entity.estimate.QEstimate.estimate;
import static com.hmdandelion.project_1410002.sales.domain.entity.estimate.QEstimateProduct.estimateProduct;

@RequiredArgsConstructor
public class EstimateRepoCustomImpl implements EstimateRepoCustom {

    private final JPAQueryFactory queryFactory;

    private OrderSpecifier createOrderSpecifier(String sort) {
        return switch (sort != null ? sort : "none") {
            case "name" -> new OrderSpecifier<>(Order.ASC, client.clientName);
            case "-name" -> new OrderSpecifier<>(Order.DESC, client.clientName);
            default -> new OrderSpecifier<>(Order.ASC, estimate.estimateCode);
        };
    }

    @Override
    public Page<EstimatesResponse> search(Pageable pageable, String sort, String clientName, String createdAt) {
        OrderSpecifier orderSpecifier = createOrderSpecifier(sort);

        List<EstimatesResponse> estimates = queryFactory
                .select(Projections.constructor(EstimatesResponse.class,
                        estimate.estimateCode,
                        estimate.createdAt,
                        client.clientName,
                        estimateProduct.quantity.multiply(estimateProduct.price).sum().as("totalPrice"),
                        estimate.status,
                        estimate.isOrdered
                ))
                .from(estimate)
                .leftJoin(client).on(estimate.clientCode.eq(client.clientCode))
                .leftJoin(estimate.estimateProducts, estimateProduct)
                .leftJoin(product).on(estimateProduct.productCode.eq(product.productCode))
                .where(
                        containClientName(clientName),
                        estimate.status.eq(EstimateStatus.ACTIVE)
                )
                .groupBy(estimate.estimateCode)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(estimate.estimateCode.countDistinct())
                .from(estimate)
                .leftJoin(client).on(estimate.clientCode.eq(client.clientCode))
                .where(
                        containClientName(clientName),
                        estimate.status.eq(EstimateStatus.ACTIVE)
                )
                .orderBy(orderSpecifier);

        return PageableExecutionUtils.getPage(estimates, pageable, countQuery::fetchOne);
    }

    private BooleanExpression containClientName(String clientName) {
        if (clientName == null || clientName.isEmpty()) {
            return null;
        }
        return client.clientName.containsIgnoreCase(clientName);
    }

    @Override
    public Optional<EstimateResponse> getEstimate(Long estimateCode) {
        Map<Long, EstimateResponse> result = queryFactory
                .from(estimate)
                .leftJoin(client).on(estimate.clientCode.eq(client.clientCode))
                .leftJoin(estimate.estimateProducts, estimateProduct)
                .leftJoin(product).on(estimateProduct.productCode.eq(product.productCode))
                .where(
                        estimate.estimateCode.eq(estimateCode),
                        estimate.status.eq(EstimateStatus.ACTIVE)
                )
                .transform(
                        GroupBy.groupBy(estimate.estimateCode).as(
                                Projections.constructor(EstimateResponse.class,
                                        estimate.estimateCode,
                                        estimate.createdAt,
                                        estimate.updatedAt,
                                        estimate.deadline,
                                        client.clientName,
                                        estimate.status,
                                        estimate.isOrdered,
                                        GroupBy.list(Projections.constructor(EstimateProductResponse.class,
                                                product.productCode,
                                                product.productName,
                                                estimateProduct.quantity,
                                                estimateProduct.price)
                                        )
                                )
                        )
                );
        EstimateResponse estimateResponse = result.get(estimateCode);
        return Optional.ofNullable(estimateResponse);

    }

}
