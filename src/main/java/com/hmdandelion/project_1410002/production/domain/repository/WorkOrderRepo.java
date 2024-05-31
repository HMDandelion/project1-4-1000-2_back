package com.hmdandelion.project_1410002.production.domain.repository;

import com.hmdandelion.project_1410002.production.domain.entity.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkOrderRepo extends JpaRepository<WorkOrder, Long>, WorkOrderRepoCustom {
}
