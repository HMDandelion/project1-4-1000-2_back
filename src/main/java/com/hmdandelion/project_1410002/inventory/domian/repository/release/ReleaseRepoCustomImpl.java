package com.hmdandelion.project_1410002.inventory.domian.repository.release;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.QStock;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.QStorage;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import com.hmdandelion.project_1410002.inventory.dto.release.response.ReleasePossible;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StorageFilterResponse;
import com.hmdandelion.project_1410002.sales.domain.entity.order.QOrder;
import com.hmdandelion.project_1410002.sales.domain.entity.order.QOrderProduct;
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
public class ReleaseRepoCustomImpl implements ReleaseRepoCustom{
    private final JPAQueryFactory queryFactory;

    public ReleaseRepoCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ReleasePossible> getReleasePossibles(Pageable pageable, Boolean isReleasePossible, Boolean createdSort) {
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QStorage storage = QStorage.storage;
        QStock stock = QStock.stock;


        return null;
    }
}
