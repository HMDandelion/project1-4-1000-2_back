package com.hmdandelion.project_1410002.production.domain.repository;

import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderRepo extends JpaRepository<WorkOrder, Long> {
}
