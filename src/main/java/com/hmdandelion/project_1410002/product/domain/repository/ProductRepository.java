package com.hmdandelion.project_1410002.product.domain.repository;

import com.hmdandelion.project_1410002.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product,Long> {
}
