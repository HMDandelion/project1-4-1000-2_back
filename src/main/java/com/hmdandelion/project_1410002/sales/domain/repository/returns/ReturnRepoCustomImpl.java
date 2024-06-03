package com.hmdandelion.project_1410002.sales.domain.repository.returns;

import com.hmdandelion.project_1410002.sales.domain.entity.returns.QReturn;
import com.hmdandelion.project_1410002.sales.domain.type.ManageType;
import com.hmdandelion.project_1410002.sales.dto.response.ReturnsResponse;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct.product;
import static com.hmdandelion.project_1410002.sales.domain.entity.client.QClient.client;
import static com.hmdandelion.project_1410002.sales.domain.entity.returns.QReturn.return$;
import static com.hmdandelion.project_1410002.sales.domain.entity.returns.QReturnProduct.returnProduct;

@RequiredArgsConstructor
public class ReturnRepoCustomImpl implements ReturnRepoCustom{
    private final JPAQueryFactory queryFactory;

    private OrderSpecifier createOrderSpecifier(String sort) {
        return switch (sort != null ? sort : "none") {
            case "returnDatetime" -> new OrderSpecifier<>(Order.ASC, return$.returnDatetime);
            case "-returnDatetime" -> new OrderSpecifier<>(Order.DESC, return$.returnDatetime);
            default -> new OrderSpecifier<>(Order.ASC, return$.returnCode);
        };
    }

    @Override
    public Page<ReturnsResponse> search(
            Pageable pageable, Long orderCode, String manageType,
            String clientName, String productName, String sort
    ) {
        OrderSpecifier orderSpecifier = createOrderSpecifier(sort);

        List<ReturnsResponse> returns = queryFactory
                .select(Projections.constructor(ReturnsResponse.class,
                        return$.returnCode,
                        return$.returnDatetime,
                        client.clientName,
                        return$.orderCode,
                        return$.manageType,
                        return$.manageStatus,
                        return$.returnStatus
                ))
                .from(return$)
                .leftJoin(client).on(client.clientCode.eq(return$.clientCode))
                .leftJoin(return$.returnProducts, returnProduct)
                .leftJoin(product).on(returnProduct.productCode.eq(product.productCode))
                .where(
                        eqOrderCode(orderCode),
                        eqManageType(manageType),
                        containClientName(clientName),
                        containProductName(productName)
                )
                .orderBy(orderSpecifier)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(return$.count())
                .from(return$)
                .leftJoin(client).on(client.clientCode.eq(return$.clientCode))
                .leftJoin(return$.returnProducts, returnProduct)
                .leftJoin(product).on(returnProduct.productCode.eq(product.productCode))
                .where(
                        eqOrderCode(orderCode),
                        eqManageType(manageType),
                        containClientName(clientName),
                        containProductName(productName)
                )
                .orderBy(orderSpecifier);

        return PageableExecutionUtils.getPage(returns, pageable, countQuery::fetchOne);
    }

    private BooleanExpression eqManageType(String manageType) {
        if (manageType == null || manageType.isEmpty()) {
            return null;
        }
        return return$.manageType.eq(ManageType.valueOf(manageType));
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

    private BooleanExpression eqOrderCode(Long orderCode) {
        if (orderCode == null) {
            return null;
        }
        return return$.orderCode.eq(orderCode);
    }
}
