package com.hmdandelion.project_1410002.sales.domain.repository.order;

import com.hmdandelion.project_1410002.sales.domain.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepo extends JpaRepository<Order, Long> {

}
