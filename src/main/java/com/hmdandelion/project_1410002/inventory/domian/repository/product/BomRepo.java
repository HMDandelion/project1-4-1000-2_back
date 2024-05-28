package com.hmdandelion.project_1410002.inventory.domian.repository.product;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BomRepo extends JpaRepository<Bom,Long> {
    Optional<Bom> findBomByProduct(Product product);
    Optional<Bom> findBomByBomCode(Long bomCode);
}
