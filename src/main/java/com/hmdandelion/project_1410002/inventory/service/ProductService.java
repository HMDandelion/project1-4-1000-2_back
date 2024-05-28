package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.QProduct;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepository;

import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.hmdandelion.project_1410002.inventory.dto.product.request.ProductRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.response.ProductsResponse;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final JPAQueryFactory queryFactory;

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

    public List<Product> searchProducts(Pageable pageable, String productName, String unit, ProductStatus status) {

        QProduct product = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if (productName != null) {
            builder.and(product.productName.contains(productName));
        }
        if (unit != null) {
            builder.and(product.unit.eq(unit));
        }
        if (status != null) {
            if (status == ProductStatus.IN_PRODUCTION) {
                builder.and(product.status.eq(ProductStatus.IN_PRODUCTION));
            } else if (status == ProductStatus.PRODUCTION_DISCONTINUED) {
                builder.and(product.status.eq(ProductStatus.PRODUCTION_DISCONTINUED));
            }
        }

        return queryFactory
                .selectFrom(product)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}
