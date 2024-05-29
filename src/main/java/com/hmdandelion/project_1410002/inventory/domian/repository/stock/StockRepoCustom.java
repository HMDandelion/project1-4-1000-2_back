package com.hmdandelion.project_1410002.inventory.domian.repository.stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.domian.type.ProductStatus;
import com.hmdandelion.project_1410002.inventory.domian.type.StockType;
import com.hmdandelion.project_1410002.inventory.dto.stock.response.StockProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface StockRepoCustom {
    Page<StockProduct> searchStocks(Pageable pageable, Long productCode, StockType type, Long minQuantity, Long maxQuantity,LocalDate startDate, LocalDate endDate);
}
