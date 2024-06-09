package com.hmdandelion.project_1410002.inventory.domian.repository.stock;

import com.hmdandelion.project_1410002.inventory.domian.entity.product.Product;
import com.hmdandelion.project_1410002.inventory.domian.entity.stock.Stock;
import com.hmdandelion.project_1410002.inventory.service.StockService;
import com.hmdandelion.project_1410002.sales.domain.entity.order.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepo extends JpaRepository<Stock,Long>, StockRepoCustom {
@Query("SELECT SUM(s.quantity) FROM Stock s WHERE s.isDelete=false")
    Integer getAccumulateStock();
@Query("SELECT SUM(s.quantity) FROM Stock s WHERE s.product.productCode = :productCode AND s.isDelete=false")
    Long getAccumulateStockByProductCode(Long productCode);

    List<Stock> findByProductProductCodeAndIsDelete(Long productCode,Boolean isDelete);

    List<Stock> findByProductProductCode(Long productCode);
    @Query("SELECT s FROM Stock s WHERE FUNCTION('DATE', s.createdAt) = CURRENT_DATE AND s.isDelete = false")
    List<Stock> findTodayStock();
}
