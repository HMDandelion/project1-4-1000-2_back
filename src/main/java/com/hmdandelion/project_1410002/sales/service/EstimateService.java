package com.hmdandelion.project_1410002.sales.service;

import com.hmdandelion.project_1410002.common.exception.BadRequestException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.sales.domain.entity.estimate.Estimate;
import com.hmdandelion.project_1410002.sales.domain.entity.estimate.EstimateProduct;
import com.hmdandelion.project_1410002.sales.domain.repository.client.ClientRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.estimate.EstimateProductRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.estimate.EstimateRepo;
import com.hmdandelion.project_1410002.sales.domain.type.EstimateStatus;
import com.hmdandelion.project_1410002.sales.dto.request.EstimateCreateRequest;
import com.hmdandelion.project_1410002.sales.dto.request.EstimateProductRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EstimateService {

    private final ClientService clientService;
    private final EstimateRepo estimateRepo;
    private final EstimateProductRepo estimateProductRepo;
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

        if(estimate.getIsOrdered()) {
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_ORDERED_ESTIMATE);
        }

        Set<Long> productRequestCodes = estimateRequest.getProducts().stream()
                .map(EstimateProductRequest::getEstimateProductCode)
                .collect(Collectors.toSet());

        List<EstimateProduct> deleteProducts = new ArrayList<>();

        estimate.getEstimateProducts().forEach(product -> {
            if (!productRequestCodes.contains(product.getEstimateProductCode()))
                deleteProducts.add(product);
        });
        estimateProductRepo.deleteAllInBatch(deleteProducts);

        List<EstimateProduct> newProducts = new ArrayList<>();

        estimateRequest.getProducts().forEach( productRequest -> {
            if(productRequest.getEstimateProductCode() == null) {
                // 새로 엔터티 생성하여 리스트에 담아 addAll
                EstimateProduct newProduct = EstimateProduct.of(
                        productRequest.quantity,
                        productRequest.getPrice(),
                        productRequest.getProductCode(),
                        estimate
                );
                newProducts.add(estimateProductRepo.save(newProduct));
            } else { // null이 아닐 경우 = 이미 존재하는 경우
                EstimateProduct existedProduct = estimateProductRepo.findById(productRequest.getEstimateProductCode())
                        .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ESTIMATE_PRODUCT_CODE));
                existedProduct.modify(
                        productRequest.getQuantity(),
                        productRequest.getPrice(),
                        productRequest.getProductCode()
                );
            }
        });

        estimate.modify(
                estimateRequest.getDeadline(),
                newProducts
        );
    }

    public void remove(Long estimateCode) {
        Estimate deletedEstimate = estimateRepo.findById(estimateCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ESTIMATE_CODE));

        if(deletedEstimate.getIsOrdered()) {
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_ORDERED_ESTIMATE);
        }

        estimateRepo.delete(deletedEstimate);
    }
}
