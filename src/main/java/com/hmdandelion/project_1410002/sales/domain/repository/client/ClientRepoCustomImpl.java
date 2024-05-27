package com.hmdandelion.project_1410002.sales.domain.repository.client;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.entity.client.QClient;
import com.hmdandelion.project_1410002.sales.domain.entity.order.QOrder;
import com.hmdandelion.project_1410002.sales.domain.entity.order.QOrderProduct;
import com.hmdandelion.project_1410002.sales.dto.response.ClientOrderDTO;
import com.hmdandelion.project_1410002.sales.model.ClientStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class ClientRepoCustomImpl implements ClientRepoCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Client> search(Pageable pageable) {
        QClient qClient = QClient.client;

        List<Client> clients = queryFactory
                .selectFrom(qClient)
                .where(
                        qClient.status.eq(ClientStatus.ACTIVE)
                )
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(qClient.count())
                .from(qClient);

        return PageableExecutionUtils.getPage(clients, pageable, countQuery::fetchOne);
    }

    @Override
    public List<ClientOrderDTO> getOrderList(Long clientCode) {
        QOrder qOrder = QOrder.order;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        return queryFactory
                .select(Projections.constructor(ClientOrderDTO.class,
                        qOrder.orderCode,
                        qOrder.orderDatetime,
                        qOrder.deadline,
                        qOrderProduct.quantity.multiply(qOrderProduct.price).sum(),
                        qOrder.status))
                .from(qOrder)
                .leftJoin(qOrder.orderProducts, qOrderProduct)
                .where(qOrder.clientCode.eq(clientCode))
                .groupBy(qOrder.orderCode)
                .fetch();
    }

    private BooleanExpression clientNameEq(String clientName) {
        return null;
    }
}
