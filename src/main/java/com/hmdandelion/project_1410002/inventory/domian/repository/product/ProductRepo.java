package com.hmdandelion.project_1410002.inventory.domian.repository.product;


import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>, ProductRepoCustom {
}
