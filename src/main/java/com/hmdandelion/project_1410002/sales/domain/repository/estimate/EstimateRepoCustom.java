package com.hmdandelion.project_1410002.sales.domain.repository.estimate;

import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import com.hmdandelion.project_1410002.sales.dto.response.EstimateResponse;
import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EstimateRepoCustom {
    Page<EstimatesResponse> search(Pageable pageable, String sort, String clientName, String createdAt);

    Optional<EstimateResponse> getEstimate(Long estimateCode);
}
