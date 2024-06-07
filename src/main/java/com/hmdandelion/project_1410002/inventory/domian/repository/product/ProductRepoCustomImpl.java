package com.hmdandelion.project_1410002.inventory.domian.repository.product;


import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepoCustomImpl implements ProductRepoCustom {

    private final JPAQueryFactory queryFactory;


    public ProductRepoCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Product> searchProducts(Pageable pageable, String productName, String unit, ProductStatus status, Boolean createdAtSort) {
        QProduct product = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if (productName != null) {
            builder.and(product.productName.contains(productName));
        }
        if (unit != null) {
            builder.and(product.unit.eq(unit));
        }
        if (status != null) {
            builder.and(product.status.eq(status));
        }

        JPAQuery<Product> query = queryFactory
                .selectFrom(product)
                .where(builder);

        // createdAtSort 값에 따라 정렬 방향 결정
        if (createdAtSort) {
            query.orderBy(product.launchDate.desc()); // true일 경우 내림차순
        } else if (!createdAtSort) {
            query.orderBy(product.launchDate.asc()); // false일 경우 오름차순
        }

        QueryResults<Product> queryResults = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults(); // fetch를 fetchResults로 변경하여 총 개수와 결과를 함께 가져옵니다.

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

}