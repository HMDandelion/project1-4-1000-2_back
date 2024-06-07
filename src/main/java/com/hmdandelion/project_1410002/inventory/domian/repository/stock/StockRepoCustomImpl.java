package com.hmdandelion.project_1410002.inventory.domian.repository.stock;


import com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.QStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StockProductDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StockRepoCustomImpl implements StockRepoCustom {

    private final JPAQueryFactory queryFactory;

    public StockRepoCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<StockProductDTO> searchStocks(Pageable pageable, Long productCode, StockType type, Long minQuantity, Long maxQuantity, AssignmentStatus assignmentStatus, LocalDate startDate, LocalDate endDate, Boolean sort) {
        QStock stock = QStock.stock;
        QProduct product = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if (productCode != null) {
            builder.and(stock.product.productCode.eq(productCode));
        }
        if (type != null) {
            builder.and(stock.type.eq(type));
        }
        if (minQuantity != null) {
            builder.and(stock.quantity.goe(minQuantity));
        }
        if (maxQuantity != null) {
            builder.and(stock.quantity.loe(maxQuantity));
        }
        if (assignmentStatus != null) {
            builder.and(stock.assignmentStatus.eq(assignmentStatus));
        }
        if (startDate != null && endDate != null) {
            builder.and(stock.createdAt.between(startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay().minusSeconds(1)));
        } else if (startDate != null) {
            builder.and(stock.createdAt.goe(startDate.atStartOfDay()));
        } else if (endDate != null) {
            builder.and(stock.createdAt.loe(endDate.plusDays(1).atStartOfDay().minusSeconds(1)));
        }

        builder.and(stock.isDelete.eq(false));

        JPAQuery<Stock> query = queryFactory
                .selectFrom(stock)
                .join(stock.product, product)
                .where(builder);

        // 정렬 조건 추가
        if (sort) {
            query.orderBy(stock.createdAt.asc());
        } else if (!sort) {
            query.orderBy(stock.createdAt.desc());
        }

        QueryResults<Stock> queryResults = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<StockProductDTO> results = queryResults.getResults().stream()
                .map(stockEntity -> {
                    StockProductDTO stockProduct = new StockProductDTO(stockEntity);
                    stockProduct.setToday(stockEntity.getCreatedAt().toLocalDate().isEqual(LocalDate.now()));
                    return stockProduct;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, queryResults.getTotal());
    }
}
