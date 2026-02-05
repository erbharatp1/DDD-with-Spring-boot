package com.bharat.ddd.order.interfaces;

import com.bharat.ddd.order.application.OrderService;
import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.dto.CreateOrderRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService service;

  public OrderController(OrderService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order create(@Validated @RequestBody CreateOrderRequest req) {
    return service.create(req.product, req.quantity);
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable String id) {
    return service.get(id);
  }

  @GetMapping
  public List<Order> getAll() {
    return service.getAll();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) {
    service.delete(id);
  }
}
