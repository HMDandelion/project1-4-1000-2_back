package com.hmdandelion.project_1410002.inventory.domian.repository.product;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<Product> searchProducts(Pageable pageable, String productName, String unit, ProductStatus status) {
        QProduct product = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if (productName != null) {
            builder.and(product.productName.contains(productName));
        }
        if (unit != null) {
            builder.and(product.unit.eq(unit));
        }
        if (status != null) {
            if (status == ProductStatus.IN_PRODUCTION) {
                builder.and(product.status.eq(ProductStatus.IN_PRODUCTION));
            } else if (status == ProductStatus.PRODUCTION_DISCONTINUED) {
                builder.and(product.status.eq(ProductStatus.PRODUCTION_DISCONTINUED));
            }
        }

        return queryFactory
                .selectFrom(product)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
