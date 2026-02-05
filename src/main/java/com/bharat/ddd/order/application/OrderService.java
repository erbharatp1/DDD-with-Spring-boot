package com.bharat.ddd.order.application;

import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import com.bharat.ddd.order.domain.OrderNotFoundException;
import com.bharat.ddd.order.domain.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository repository;

  public OrderService(OrderRepository repository) {
    this.repository = repository;
  }

  public Order create(String product, int quantity) {
    Order order = new Order(new OrderId(), product, quantity);
    return repository.save(order);
  }

  public Order get(String id) {
    return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
  }

  public List<Order> getAll() {
    return repository.findAll();
  }

  public void delete(String id) {
    if (repository.findById(id).isEmpty()) {
      throw new OrderNotFoundException(id);
    }
    repository.deleteById(id);
  }
}
