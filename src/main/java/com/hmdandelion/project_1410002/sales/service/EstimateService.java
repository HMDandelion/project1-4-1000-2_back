package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.sales.domain.entity.estimate.Estimate;
import com.hmdandelion.project_1410002.sales.domain.entity.estimate.EstimateProduct;
import com.hmdandelion.project_1410002.sales.domain.repository.client.ClientRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.estimate.EstimateRepo;
import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import com.hmdandelion.project_1410002.sales.dto.request.EstimateCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.request.EstimateUpdateRequest;
import com.hmdandelion.project_1410002.sales.dto.response.EstimateResponse;
import com.hmdandelion.project_1410002.sales.dto.response.EstimatesResponse;
import com.hmdandelion.project_1410002.sales.domain.type.ClientStatus;
import com.hmdandelion.project_1410002.sales.domain.type.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateService {

    private final ClientService clientService;
    private final EstimateRepo estimateRepo;
    private final ClientRepo clientRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10);
    }

    @Transactional(readOnly = true)
    public Page<EstimatesResponse> getEstimates(Integer page, String sort, String clientName, String createdAt) {
        Page<EstimatesResponse> estimates = estimateRepo.search(getPageable(page), sort, clientName, createdAt);

        return estimates;
    }

    @Transactional(readOnly = true)
    public EstimateResponse getEstimate(Long estimateCode) {
        EstimateResponse estimate = estimateRepo.getEstimate(estimateCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ESTIMATE_CODE));
        return estimate;
    }

    public Long save(EstimateCreateRequest estimateRequest) {

        if(estimateRequest.getClientCode() == null) {
            estimateRequest.setClientCode(clientService.save(estimateRequest.getClient(), ClientType.PRODUCTS));
        }
        clientRepo.findByClientCodeAndStatusNot(estimateRequest.getClientCode(), ClientStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE));

        final Estimate newEstimate = Estimate.of(
                estimateRequest.getDeadline(),
                estimateRequest.getClientCode()
        );

        List<EstimateProduct> products = estimateRequest.getProducts().stream()
                .map(productRequest -> {
                    return EstimateProduct.of(
                            productRequest.getQuantity(),
                            productRequest.getPrice(),
                            productRequest.getProductCode(),
                            newEstimate
                    );
                }).toList();

        newEstimate.modifyProducts(products);
        final Estimate estimate = estimateRepo.save(newEstimate);

        return estimate.getEstimateCode();
    }

    public void modify(Long estimateCode, EstimateUpdateRequest estimateRequest) {
        Estimate estimate = estimateRepo.findByEstimateCodeAndStatusNot(estimateCode, EstimateStatus.DELETED)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ESTIMATE_CODE));

        estimate.getEstimateProducts().clear();

        List<EstimateProduct> products = estimateRequest.getProducts().stream()
                .map(productRequest -> {
                    return EstimateProduct.of(
                            productRequest.getQuantity(),
                            productRequest.getPrice(),
                            productRequest.getProductCode(),
                            estimate
                    );
                }).toList();

        estimate.modify(
                estimateRequest.getDeadline(),
                products
        );
    }

    public void remove(Long estimateCode) {
        estimateRepo.deleteById(estimateCode);
    }
}
