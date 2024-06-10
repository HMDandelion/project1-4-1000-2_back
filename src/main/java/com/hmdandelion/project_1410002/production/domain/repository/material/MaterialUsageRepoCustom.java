package com.hmdandelion.project_1410002.production.domain.repository.material;

import com.hmdandelion.project_1410002.production.domain.entity.material.MaterialUsage;
import com.hmdandelion.project_1410002.production.dto.material.MaterialUsageDTO;
import com.hmdandelion.project_1410002.production.dto.material.response.MaterialUsageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialUsageRepoCustom  {

    List<MaterialUsageDTO> searchUse(Pageable pageable, List<Long> stockCodes, String sortType);

    MaterialUsageResponse getMaterialUsage(Long usageCode);
}
