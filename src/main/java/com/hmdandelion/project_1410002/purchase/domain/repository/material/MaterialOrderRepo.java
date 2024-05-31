package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialOrderRepo extends JpaRepository<MaterialOrder, Long>, MaterialOrderRepoCustom {

    List<MaterialOrder> findByPlanCode(Long planCode);
}
