package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.ProductSpec;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductSpecRepo;
import com.hmdandelion.project_1410002.inventory.dto.product.request.ProductSpecRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.response.ProductSpecResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductSpecService {

    private final ProductSpecRepo productSpecRepo;
    private final ProductRepo productRepo;


    public void saveProductSpec(Long productCode, ProductSpecRequest productSpecRequest) {
        productRepo.findById(productCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        ProductSpec productSpec = ProductSpec.of(
                productSpecRequest.getColor(),
                productSpecRequest.getType(),
                productSpecRequest.getSize(),
                productCode
        );
        productSpecRepo.save(productSpec);
    }
    @Transactional(readOnly = true)
    public List<ProductSpecResponse> getProductSpecs() {
        List<ProductSpec> products = productSpecRepo.findAll();
        System.out.println("products = " + products);

        return products.stream().map(productSpec -> {
            Product product = productRepo.findById(productSpec.getProductCode())
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));

            return  ProductSpecResponse.of(
                    productSpec.getColor(),
                    productSpec.getType(),
                    productSpec.getSize(),
                    productSpec.getProductCode(),
                    product.getProductName(), // Product에서 조회한 정보를 설정
                    product.getLaunchDate(),
                    product.getPrice(),
                    product.getUnit(),
                    product.getUpdatedAt(),
                    product.getStatus()
            );
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductSpecResponse> getProductSpec(final Long productCode) {
        Product product = productRepo.findById(productCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        List<ProductSpec> productSpecs = productSpecRepo.findProductSpecsByProductCode(productCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));

        return productSpecs.stream().map(productSpec -> ProductSpecResponse.of(
                productSpec.getColor(),
                productSpec.getType(),
                productSpec.getSize(),
                productSpec.getProductCode(),
                product.getProductName(), // Product에서 조회한 정보를 설정
                product.getLaunchDate(),
                product.getPrice(),
                product.getUnit(),
                product.getUpdatedAt(),
                product.getStatus()
        )).collect(Collectors.toList());
    }


    public void modifyProductSpecByProductSpecCode(Long code,ProductSpecRequest productSpecRequest) {
        ProductSpec productSpec = productSpecRepo.findById(code).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        System.out.println("productSpecRequest"+productSpecRequest);
        System.out.println("productSpec = " + productSpec);
        productSpec.modify(
                productSpecRequest.getColor(),
                productSpecRequest.getType(),
                productSpecRequest.getSize()
        );
        System.out.println("수정 완료");
    }

    public void deleteProductSpec(Long code) {
        productSpecRepo.deleteById(code);
    }
}
