package com.bharat.ddd.order.interfaces;

import com.bharat.ddd.order.application.OrderService;
import com.bharat.ddd.order.domain.Order;
import java.util.List;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class OrderGraphqlController {

  private final OrderService service;

  public OrderGraphqlController(OrderService service) {
    this.service = service;
  }

  @QueryMapping
  public List<Order> orders() {
    return service.getAll();
  }
}
