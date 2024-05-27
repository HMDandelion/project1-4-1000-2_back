package com.hmdandelion.project_1410002.product.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.product.domain.dto.request.ProductRequest;
import com.hmdandelion.project_1410002.product.domain.dto.response.ProductsResponse;
import com.hmdandelion.project_1410002.product.domain.entity.Product;
import com.hmdandelion.project_1410002.product.domain.repository.ProductRepository;
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
public class ProductService {

    private final ProductRepository productRepository;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
    }


    public Page<ProductsResponse> getProducts(Integer page) {
        Page<Product> products = productRepository.findAll(getPageable(page));
        return products.map(ProductsResponse::from);
    }


    public ProductsResponse getProduct(Long productCode) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        return ProductsResponse.from(product);
    }

    public Long save(ProductRequest productRequest) {

        final Product newProduct = Product.of(
                productRequest.getProductName(),
                productRequest.getPrice(),
                productRequest.getUnit()
        );

        final Product product = productRepository.save(newProduct);

        return product.getProductCode();
    }

    public void modifyProduct(Long productCode, ProductRequest productRequest) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        product.modify(
                productRequest.getProductName(),
                productRequest.getPrice(),
                productRequest.getUnit()
        );
    }

    public void updateStatus(Long productCode) {
        Product product = productRepository.findById(productCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        product.updateStatus(product);
    }
}
