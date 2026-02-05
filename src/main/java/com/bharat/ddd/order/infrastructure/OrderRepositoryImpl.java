package com.bharat.ddd.order.infrastructure;

import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import com.bharat.ddd.order.domain.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository jpa;

  public OrderRepositoryImpl(OrderJpaRepository jpa) {
    this.jpa = jpa;
  }

  public Order save(Order order) {
    OrderEntity entity =
        new OrderEntity(order.getId().getValue(), order.getProduct(), order.getQuantity());
    jpa.save(entity);
    return order;
  }

  public java.util.Optional<Order> findById(String id) {
    return jpa.findById(id)
        .map(e -> new Order(new OrderId(e.getId()), e.getProduct(), e.getQuantity()));
  }

  public List<Order> findAll() {
    return jpa.findAll().stream()
        .map(e -> new Order(new OrderId(e.getId()), e.getProduct(), e.getQuantity()))
        .collect(Collectors.toList());
  }

  public void deleteById(String id) {
    jpa.deleteById(id);
  }
}
