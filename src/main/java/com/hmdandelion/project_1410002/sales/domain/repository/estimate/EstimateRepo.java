package com.hmdandelion.project_1410002.sales.domain.repository.estimate;

import com.hmdandelion.project_1410002.sales.domain.entity.estimate.Estimate;
import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstimateRepo extends JpaRepository<Estimate, Long>, EstimateRepoCustom{

    Optional<Estimate> findByEstimateCodeAndStatusNot(Long estimateCode, EstimateStatus estimateStatus);
}
