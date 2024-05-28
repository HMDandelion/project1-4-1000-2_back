package com.hmdandelion.project_1410002.sales.domain.repository.estimate;

import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstimateRepoCustom {
    Page<EstimatesResponse> search(Pageable pageable, String sort, String clientName, String createdAt);
}
