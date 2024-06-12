package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.hmdandelion.project_1410002.inventory.dto.product.request.ProductRequest;
import com.hmdandelion.project_1410002.inventory.dto.product.response.ProductsResponse;
import com.hmdandelion.project_1410002.inventory.dto.product.response.SimpleProductResponse;
import com.hmdandelion.project_1410002.sales.domain.entity.client.Client;
import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import com.hmdandelion.project_1410002.sales.domain.entity.order.OrderProduct;
import com.hmdandelion.project_1410002.sales.domain.repository.client.ClientRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.order.OrderProductRepo;
import com.hmdandelion.project_1410002.sales.domain.repository.order.OrderRepo;
import com.hmdandelion.project_1410002.sales.domain.type.ClientStatus;
import com.hmdandelion.project_1410002.sales.domain.type.OrderStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.hmdandelion.project_1410002.sales.domain.type.ClientStatus.ACTIVE;
import static com.hmdandelion.project_1410002.sales.domain.type.ClientStatus.DELETED;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepo productRepo;
    private final OrderProductRepo orderProductRepo;
    private final OrderRepo orderRepo;
    private final ClientRepo clientRepo;

    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("productCode").descending());
    }

    @Transactional(readOnly = true)
    public List<SimpleProductResponse> getSimpleProducts() {
        List<Product> products = productRepo.findByStatus(ProductStatus.IN_PRODUCTION);
        return products.stream().map(SimpleProductResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Page<ProductsResponse> getProducts(Integer page) {
        Page<Product> products = productRepo.findAll(getPageable(page));
        return products.map(ProductsResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductsResponse getProduct(Long productCode) {
        Product product = productRepo.findById(productCode)
                                           .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        return ProductsResponse.from(product);
    }

    public Long save(ProductRequest productRequest) {

        final Product newProduct = Product.of(
                productRequest.getProductName(),
                productRequest.getPrice(),
                productRequest.getUnit()
        );

        final Product product = productRepo.save(newProduct);

        return product.getProductCode();
    }

    public void modifyProduct(Long productCode, ProductRequest productRequest) {
        Product product = productRepo.findById(productCode)
                                           .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        product.modify(
                productRequest.getProductName(),
                productRequest.getPrice(),
                productRequest.getUnit()
        );
    }

    public void updateStatus(Long productCode) {
        Product product = productRepo.findById(productCode)
                                           .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        product.updateStatus(product);
    }
    @Transactional(readOnly = true)
    public Page<Product> searchProducts(Pageable pageable, String productName, String unit, ProductStatus status,Boolean createdAtSort) {
        return productRepo.searchProducts(pageable, productName, unit, status,createdAtSort);
    }
    @Transactional(readOnly = true)
    public List<String> getProductClient(Long productCode) {
        Set<String> resultSet = new HashSet<>();
        List<OrderProduct> orderProducts = orderProductRepo.findByProductCode(productCode);
        for(OrderProduct orderProduct : orderProducts) {
            try {
                Order order = orderRepo.findByOrderCodeAndStatus(orderProduct.getOrder().getOrderCode(), OrderStatus.ORDER_RECEIVED).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_ORDER_CODE));
                Client client = clientRepo.findByClientCodeAndStatusNot(order.getClientCode(), DELETED).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CLIENT_CODE));

                resultSet.add(client.getClientName());
            } catch (NotFoundException e) {
                return Collections.emptyList();
            }
        }
        return new ArrayList<>(resultSet);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        List<Product> productList = productRepo.findAll();
        return productList;
    }
}
