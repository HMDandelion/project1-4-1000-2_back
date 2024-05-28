package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.entity.estimate.Estimate;
import com.hmdandelion.project_1410002.sales.domain.repository.estimate.EstimateRepo;
import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import com.hmdandelion.project_1410002.sales.dto.response.SalesClientsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateService {

    private final EstimateRepo estimateRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10);
    }

    @Transactional(readOnly = true)
    public Page<EstimatesResponse> getEstimates(Integer page, String sort, String clientName, String createdAt) {
        Page<EstimatesResponse> estimates = estimateRepo.search(getPageable(page), sort, clientName, createdAt);

        return estimates;
    }
}
