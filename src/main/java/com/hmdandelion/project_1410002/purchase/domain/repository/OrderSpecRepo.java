package com.hmdandelion.project_1410002.purchase.domain.repository;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.OrderSpec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSpecRepo extends JpaRepository<OrderSpec,Long> {

    List<OrderSpec> findByMaterialSpecSpecCode(long specCode);
}
