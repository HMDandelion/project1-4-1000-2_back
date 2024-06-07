package com.hmdandelion.project_1410002.sales.domain.repository.order;

import com.hmdandelion.project_1410002.sales.domain.entity.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepo extends JpaRepository<OrderProduct,Long> {
    @Query("SELECT op FROM OrderProduct op WHERE op.order.orderCode = :orderCode")
    List<OrderProduct> findByOrderCode(@Param("orderCode") Long orderCode);
    /*동환 : 메소드 추가*/
    List<OrderProduct> findByProductCode(Long productCode);
}
