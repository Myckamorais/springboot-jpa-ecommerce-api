package com.myckael.ecommerce_api.repositories;

import com.myckael.ecommerce_api.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
