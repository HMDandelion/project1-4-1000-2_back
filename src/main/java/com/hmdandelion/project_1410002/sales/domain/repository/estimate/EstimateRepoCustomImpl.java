package com.hmdandelion.project_1410002.sales.domain.repository.estimate;

import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.hmdandelion.project_1410002.sales.domain.entity.client.QClient.client;
import static com.hmdandelion.project_1410002.sales.domain.entity.estimate.QEstimate.estimate;
import static com.hmdandelion.project_1410002.sales.domain.entity.estimate.QEstimateProduct.estimateProduct;

@RequiredArgsConstructor
public class EstimateRepoCustomImpl implements EstimateRepoCustom{
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
                .groupBy(estimate.estimateCode)
                .fetch();
        return null;
    }
}
