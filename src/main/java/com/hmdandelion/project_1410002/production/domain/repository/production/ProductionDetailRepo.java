package com.hmdandelion.project_1410002.production.domain.repository.production;

import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionDetail;
import com.hmdandelion.project_1410002.production.domain.entity.production.ProductionManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface  ProductionDetailRepo extends JpaRepository<ProductionDetail,Long> {


}
