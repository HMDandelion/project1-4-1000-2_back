package com.hmdandelion.project_1410002.inventory.domian.repository.stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.service.StockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepo extends JpaRepository<Stock,Long>, StockRepoCustom {
@Query("SELECT SUM(s.quantity) FROM Stock s WHERE s.isDelete=false")
    Integer getAccumulateStock();
@Query("SELECT SUM(s.quantity) FROM Stock s WHERE s.product.productCode = :productCode AND s.isDelete=false")
    Integer getAccumulateStockByProductCode(Integer productCode);
}
