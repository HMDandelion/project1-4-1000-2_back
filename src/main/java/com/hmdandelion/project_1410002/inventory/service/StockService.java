package com.hmdandelion.project_1410002.inventory.service;

import com.hmdandelion.project_1410002.common.exception.BadRequestException;
import com.hmdandelion.project_1410002.common.exception.CustomException;
import com.hmdandelion.project_1410002.common.exception.NotFoundException;
import com.hmdandelion.project_1410002.common.exception.type.ExceptionCode;
import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Storage;
import com.hmdandelion.project_1410002.inventory.domian.repository.product.ProductRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StockRepo;
import com.hmdandelion.project_1410002.inventory.domian.repository.stock.StorageRepo;
import com.hmdandelion.project_1410002.inventory.domian.type.AssignmentStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import com.hmdandelion.project_1410002.inventory.dto.product.response.AccumulateProduct;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockCreateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.request.StockUpdateRequest;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.LeftStockDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StockProductDTO;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.TodayStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepo stockRepo;
    private final ProductRepo productRepo;
    private final StorageRepo storageRepo;
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
    @Transactional(readOnly = true)
    public Page<StockProductDTO> searchStocks(Pageable pageable, Long productCode, StockType type, Long minQuantity, Long maxQuantity, AssignmentStatus assignmentStatus, LocalDate startDate, LocalDate endDate, Boolean sort) {
        return stockRepo.searchStocks(pageable, productCode, type, minQuantity,maxQuantity,assignmentStatus,startDate,endDate,sort);
    }

    public void modifyStock(Long stockCode, StockUpdateRequest stockUpdateRequest) {
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        if(stock.getIsDelete()==true){
            throw new CustomException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }
        Product product = productRepo.findById(stockUpdateRequest.getProductCode()).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));

        stock.modify(
                product,
                stockUpdateRequest.getType()
        );
    }

    public void deleteStockByStockCode(Long stockCode) {
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        if(stock.getIsDelete()==true){
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }
        System.out.println("stock.getAssignmentStatus() = " + stock.getAssignmentStatus());
        if(stock.getAssignmentStatus()!=AssignmentStatus.NOT_ASSIGNED){
            throw new CustomException(ExceptionCode.ALREADY_ASSIGNED_STOCK);
        }
        stockRepo.deleteById(stockCode);
    }
    @Transactional(readOnly = true)
    public Integer getAccumulateStock() {
        Integer sum = stockRepo.getAccumulateStock();
        return sum;
    }
    @Transactional(readOnly = true)
    public List<AccumulateProduct> getAccumulateStockByProductCode() {
        List<AccumulateProduct> resultList = new ArrayList<>();
        List<Product> products = productRepo.findAll();
        Integer totalSum = stockRepo.getAccumulateStock();
        for(Product product : products){
            Long sum = stockRepo.getAccumulateStockByProductCode(product.getProductCode());
            System.out.println("product.getProductCode() = " + product.getProductCode());
            System.out.println("totalSum = " + totalSum);
            System.out.println("sum = " + sum);
            if(sum == null){
                sum = 0L;
            }
            Double ratio = (double)sum/totalSum;
            BigDecimal ratioRounded = new BigDecimal(ratio).setScale(2, RoundingMode.HALF_UP);
            AccumulateProduct accumulateProduct = AccumulateProduct.of(
                    product.getProductName(),
                    sum,
                    ratioRounded.doubleValue()
            );
            resultList.add(accumulateProduct);
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    public StockProductDTO getStock(Long stockCode) {
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        if(stock.getIsDelete()==true){
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }
        Product product = productRepo.findById(stock.getProduct().getProductCode()).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PRODUCT_CODE));
        Boolean isToday = stock.getCreatedAt().toLocalDate().isEqual(LocalDate.now());

        StockProductDTO stockProduct = StockProductDTO.of(
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


    public TodayStockDTO getTodayStockInformation() {

        List<Stock> stocks = stockRepo.findTodayStock();

        Long todayQuantity = 0L;
        for(Stock stock : stocks){
            todayQuantity += stock.getQuantity();
        }

        TodayStockDTO todayStockDTO = TodayStockDTO.of(
                LocalDate.now(),
                stocks.size(),
                todayQuantity
        );
        return todayStockDTO;
    }

    public LeftStockDTO getLeftStock(Long stockCode) {
        List<Storage> storages = storageRepo.findStoragesByStockStockCodeAndIsDelete(stockCode,false);
        Stock stock = stockRepo.findById(stockCode).orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_STOCK_CODE));
        if(stock.getIsDelete()==true){
            throw new BadRequestException(ExceptionCode.BAD_REQUEST_DELETED_STOCK);
        }
        Long initialQuantity = stock.getQuantity();
        Long assignmentQuantity = 0L;
        Long leftQuantity = 0L;
        for(Storage storage : storages){
            assignmentQuantity += storage.getInitialQuantity();
        }
        leftQuantity = initialQuantity - assignmentQuantity;
        LeftStockDTO leftStockDTO = LeftStockDTO.of(
          initialQuantity,
          assignmentQuantity,
          leftQuantity
        );
        return leftStockDTO;
    }
}
