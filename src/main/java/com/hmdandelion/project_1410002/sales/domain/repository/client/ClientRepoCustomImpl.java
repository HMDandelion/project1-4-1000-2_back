package com.hmdandelion.project_1410002.sales.domain.repository.client;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.dto.response.ClientOrderDTO;
import com.hmdandelion.project_1410002.sales.model.ClientStatus;
import com.hmdandelion.project_1410002.sales.model.OrderStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.hmdandelion.project_1410002.sales.domain.entity.client.QClient.client;
import static com.hmdandelion.project_1410002.sales.domain.entity.order.QOrder.order;
import static com.hmdandelion.project_1410002.sales.domain.entity.order.QOrderProduct.orderProduct;

@RequiredArgsConstructor
public class ClientRepoCustomImpl implements ClientRepoCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Client> search(Pageable pageable, String sort, String clientName, Boolean isOrdered) {

        List<Client> clients = queryFactory
                .selectFrom(client)
                .where(
                        containClientName(clientName),
                        isOrdered(isOrdered),
                        client.status.eq(ClientStatus.ACTIVE)
                )
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(client.count())
                .where(
                        containClientName(clientName),
                        isOrdered(isOrdered),
                        client.status.eq(ClientStatus.ACTIVE)
                )
                .from(client);

        return PageableExecutionUtils.getPage(clients, pageable, countQuery::fetchOne);
    }

    private BooleanExpression containClientName(String clientName) {
        if(clientName == null || clientName.isEmpty()) {
            return null;
        }
        return client.clientName.containsIgnoreCase(clientName);
    }

    private BooleanExpression isOrdered(Boolean isOrdered) {
        if(isOrdered == null || !isOrdered) {
            return null;
        }
        return JPAExpressions
                .selectOne()
                .from(order)
                .where(
                        order.clientCode.eq(client.clientCode),
                        order.status.ne(OrderStatus.COMPLETED)
                )
                .exists();
    }

    @Override
    public List<ClientOrderDTO> getOrderList(Long clientCode) {

        return queryFactory
                .select(Projections.constructor(ClientOrderDTO.class,
                        order.orderCode,
                        order.orderDatetime,
                        order.deadline,
                        orderProduct.quantity.multiply(orderProduct.price).sum(),
                        order.status))
                .from(order)
                .leftJoin(order.orderProducts, orderProduct)
                .where(order.clientCode.eq(clientCode))
                .groupBy(order.orderCode)
                .fetch();
    }

}
