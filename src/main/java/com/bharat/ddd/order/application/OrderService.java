package com.bharat.ddd.order.application;

import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import com.bharat.ddd.order.domain.OrderNotFoundException;
import com.bharat.ddd.order.domain.OrderRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order create(String product, int quantity) {
    Order order = new Order(new OrderId(), product, quantity);
    log.info("Creating order: {}", order);
    return orderRepository.save(order);
  }



  public Order get(String id) {
    log.info("Getting order with id {}", id);
    return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
  }

  public List<Order> getAll() {
    log.info("Getting all orders");
    return orderRepository.findAll();
  }

  public void delete(String id) {
    if (orderRepository.findById(id).isEmpty()) {
      log.warn("Order with id {} not found", id);
      throw new OrderNotFoundException(id);
    }
    orderRepository.deleteById(id);
    log.info("Order with id {} deleted", id);
  }
}
