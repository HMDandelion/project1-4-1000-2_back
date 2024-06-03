package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StockRepo;
import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockUpdateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StockProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepo stockRepo;
    private final ProductRepo productRepo;
    private Pageable getPageable(final Integer page) {
        return PageRequest.of(page - 1, 10, Sort.by("productCode"));
    }

    public Long saveStock(
            final StockCreateRequest stockCreateRequest
            ) {
        Product product = productRepo.findById(stockCreateRequest.getProductCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        final Stock newStock = Stock.of(
            stockCreateRequest.getQuantity(),
            stockCreateRequest.getType(),
            product
        );
        Stock stock = stockRepo.save(newStock);
        return stock.getStockCode();
    }

    public Page<StockProduct> searchStocks(Pageable pageable, Long productCode, StockType type, Long minQuantity, Long maxQuantity, AssignmentStatus assignmentStatus,LocalDate startDate, LocalDate endDate,Boolean sort) {
        return stockRepo.searchStocks(pageable, productCode, type, minQuantity,maxQuantity,assignmentStatus,startDate,endDate,sort);
    }

    public void modifyStock(Long stockCode, StockUpdateRequest stockUpdateRequest) {
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        if(stock.getIsDelete()==true){
            throw new CustomException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }
        Product product = productRepo.findById(stockUpdateRequest.getProductCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));

        stock.modify(
                product,
                stockUpdateRequest.getType()
        );
    }

    public void deleteStockByStockCode(Long stockCode) {
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        if(stock.getIsDelete()==true){
            throw new CustomException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }
        System.out.println("stock.getAssignmentStatus() = " + stock.getAssignmentStatus());
        if(stock.getAssignmentStatus()!=AssignmentStatus.NOT_ASSIGNED){
            throw new CustomException(ExceptionCode.ALREADY_ASSIGNED_STOCK);
        }
        stockRepo.deleteById(stockCode);
    }

    public Integer getAccumulateStock() {
        Integer sum = stockRepo.getAccumulateStock();
        return sum;
    }

    public Integer getAccumulateStockByProductCode(Integer productCode) {
        Integer sum = stockRepo.getAccumulateStockByProductCode(productCode);
        return sum;
    }

    public StockProduct getStock(Long stockCode) {
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        if(stock.getIsDelete()==true){
            throw new CustomException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }
        Product product = productRepo.findById(stock.getProduct().getProductCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        Boolean isToday = stock.getCreatedAt().toLocalDate().isEqual(LocalDate.now());

        StockProduct stockProduct = StockProduct.of(
                stock.getStockCode(),
                stock.getQuantity(),
                stock.getCreatedAt(),
                stock.getIsDelete(),
                stock.getAssignmentStatus(),
                stock.getType(),
                product.getProductCode(),
                product.getProductName(),
                product.getLaunchDate(),
                product.getPrice(),
                product.getUnit(),
                product.getUpdatedAt(),
                product.getStatus(),
                isToday
        );
        return stockProduct;
    }
}
