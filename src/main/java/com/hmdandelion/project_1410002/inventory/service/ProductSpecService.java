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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("code"));
    }

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
                    productSpec.getCode(),
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
    public Page<ProductSpecResponse> getProductSpec(final Long productCode, final Integer page) {
        Product product = productRepo.findById(productCode)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        Page<ProductSpec> productSpecs = productSpecRepo.findProductSpecsByProductCode(getPageable(page), productCode);

        // ProductSpec 객체를 ProductSpecResponse 객체로 변환
        Page<ProductSpecResponse> productSpecResponses = productSpecs.map(productSpec -> {
            // 여기서는 ProductSpecResponse의 모든 필드를 채우기 위해 예시로 Product 객체의 일부 정보를 사용합니다.
            // 실제 구현에서는 필요에 맞게 Product 정보를 가져와야 할 수도 있습니다.
            return ProductSpecResponse.of(
                    productSpec.getCode(),
                    productSpec.getColor(),
                    productSpec.getType(),
                    productSpec.getSize(),
                    productSpec.getProductCode(),
                    product.getProductName(), // 예시로 Product의 이름을 사용합니다. 실제로는 별도의 로직이 필요할 수 있습니다.
                    product.getLaunchDate(),
                    product.getPrice(),
                    product.getUnit(),
                    product.getUpdatedAt(),
                    product.getStatus()
            );
        });

        return productSpecResponses;
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
