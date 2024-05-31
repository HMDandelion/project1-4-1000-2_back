package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import com.hmdandelion.project_1410002.purchase.dto.material.MaterialOrderDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialOrderRepo extends JpaRepository<MaterialOrder, Long>, MaterialOrderRepoCustom {

    List<MaterialOrder> findByPlanCode(Long planCode);

}
