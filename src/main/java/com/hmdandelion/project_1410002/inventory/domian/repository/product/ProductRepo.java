package com.hmdandelion.project_1410002.inventory.domian.repository.product;


import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, ProductRepoCustom {
    List<Product> findByStatus(ProductStatus productStatus);
}
