package com.hmdandelion.project_1410002.inventory.domian.repository.product;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Bom;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BomRepo extends JpaRepository<Bom,Long> {
    List<Bom> findBomByProduct(Product product);
    Page<Bom> findByProductProductCode(Pageable pageable, Long productCode);
    Optional<Bom> findBomByBomCode(Long bomCode);
}

