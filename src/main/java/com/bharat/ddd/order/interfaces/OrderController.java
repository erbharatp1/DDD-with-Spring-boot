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

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order create(@Validated @RequestBody CreateOrderRequest req) {
    return orderService.create(req.product, req.quantity);
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable String id) {
    return orderService.get(id);
  }

  @GetMapping
  public List<Order> getAll() {
    return orderService.getAll();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) {
    orderService.delete(id);
  }
}
