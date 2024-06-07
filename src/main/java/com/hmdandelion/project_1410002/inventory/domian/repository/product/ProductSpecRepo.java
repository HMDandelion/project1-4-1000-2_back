package com.hmdandelion.project_1410002.inventory.domian.repository.product;


import com.hmdandelion.project_1410002.inventory.domian.entity.product.ProductSpec;
import com.hmdandelion.project_1410002.inventory.dto.product.response.ProductSpecResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSpecRepo extends JpaRepository<ProductSpec,Long> {
    Optional<List<ProductSpec>> findProductSpecsByProductCode(Long productCode);
}
