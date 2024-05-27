package com.hmdandelion.project_1410002.product.domain.repository;

import com.hmdandelion.project_1410002.product.domain.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product,Long>, QuerydslPredicateExecutor<Product> {
//    // QueryDSL 메서드 추가
//    List<Product> findDynamicQuery(String name, Pageable pageable);
}
