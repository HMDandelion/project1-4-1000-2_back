package com.hmdandelion.project_1410002.production.domain.repository;


import com.hmdandelion.project_1410002.production.domain.entity.ProductionPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductionRepository extends JpaRepository <ProductionPlan, Long> {

    Page<ProductionPlan> findByStatusNot(Pageable pageable);
}