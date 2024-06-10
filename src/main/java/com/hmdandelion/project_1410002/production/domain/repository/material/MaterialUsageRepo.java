package com.hmdandelion.project_1410002.production.domain.repository.material;

import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialUsageRepo extends JpaRepository<MaterialUsage, Long>, MaterialUsageRepoCustom {
}
