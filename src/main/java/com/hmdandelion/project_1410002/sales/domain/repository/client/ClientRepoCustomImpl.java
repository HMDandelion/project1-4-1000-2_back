package com.hmdandelion.project_1410002.sales.domain.repository.client;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.entity.client.QClient;
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
        List<Client> clients = queryFactory
                .selectFrom(QClient.client)
                // .where
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(QClient.client.count())
                .from(QClient.client);

        return PageableExecutionUtils.getPage(clients, pageable, countQuery::fetchOne);
    }
}
