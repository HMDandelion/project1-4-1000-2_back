package com.hmdandelion.project_1410002.inventory.domian.repository.material;

import com.hmdandelion.project_1410002.inventory.domian.entity.material.MaterialStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialStockRepo extends JpaRepository<MaterialStock,Long> {
}
