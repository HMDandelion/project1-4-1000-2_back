package com.hmdandelion.project_1410002.sales.domain.repository.order;

import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import com.hmdandelion.project_1410002.sales.domain.type.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderRepo extends JpaRepository<Order, Long>, OrderRepoCustom {

    Optional<Order> findByOrderCodeAndStatus(Long orderCode, OrderStatus orderStatus);
}
