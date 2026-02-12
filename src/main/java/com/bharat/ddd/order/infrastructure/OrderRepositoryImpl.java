package com.bharat.ddd.order.infrastructure;

import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import com.bharat.ddd.order.domain.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;

  public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository) {
    this.orderJpaRepository = orderJpaRepository;
  }

  public Order save(Order order) {
    log.info("Saving order: {}", order);
    OrderEntity entity =
        new OrderEntity(order.getId().getValue(), order.getProduct(), order.getQuantity());
    orderJpaRepository.save(entity);
    log.info("Saved order: {}", order);
    return order;
  }

  public java.util.Optional<Order> findById(String id) {
    log.info("Finding order with id {}", id);
    return orderJpaRepository
        .findById(id)
        .map(e -> new Order(new OrderId(e.getId()), e.getProduct(), e.getQuantity()));
  }

  public List<Order> findAll() {
    log.info("Finding all orders");
    return orderJpaRepository.findAll().stream()
        .map(e -> new Order(new OrderId(e.getId()), e.getProduct(), e.getQuantity()))
        .collect(Collectors.toList());
  }

  public void deleteById(String id) {
    log.info("Deleting order with id {}", id);
    orderJpaRepository.deleteById(id);
  }
}
