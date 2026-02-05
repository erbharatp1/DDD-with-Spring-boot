package com.bharat.ddd.order.application;

import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import com.bharat.ddd.order.domain.OrderNotFoundException;
import com.bharat.ddd.order.domain.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order create(String product, int quantity) {
    Order order = new Order(new OrderId(), product, quantity);
    return orderRepository.save(order);
  }

  public Order get(String id) {
    return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
  }

  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  public void delete(String id) {
    if (orderRepository.findById(id).isEmpty()) {
      throw new OrderNotFoundException(id);
    }
    orderRepository.deleteById(id);
  }
}
