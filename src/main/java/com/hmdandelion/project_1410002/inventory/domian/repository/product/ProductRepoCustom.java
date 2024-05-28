package com.hmdandelion.project_1410002.inventory.domian.repository.product;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepoCustom {
    List<Product> searchProducts(Pageable pageable, String productName, String unit, ProductStatus status);
}

