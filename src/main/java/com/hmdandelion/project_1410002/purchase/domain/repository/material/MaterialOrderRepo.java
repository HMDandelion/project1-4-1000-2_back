package com.hmdandelion.project_1410002.purchase.domain.repository.material;

import com.hmdandelion.project_1410002.purchase.domain.entity.material.MaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaterialOrderRepo extends JpaRepository<MaterialOrder,Long> {

    @Query("SELECT mo " +
            "FROM MaterialOrder mo " +
            "JOIN OrderSpec oc ON mo.orderCode = oc.orderCode " +
            "WHERE FUNCTION('YEAR', mo.orderDate) = :year " +
            "AND FUNCTION('MONTH', mo.orderDate) = :month " +
            "AND oc.materialSpec.specCode = :speCode")
    List<MaterialOrder> findOrdersByYearAndMonth(int year, int month, long specCode);
}
