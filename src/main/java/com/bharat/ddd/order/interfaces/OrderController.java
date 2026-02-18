package com.bharat.ddd.order.interfaces;

import com.bharat.ddd.order.application.OrderService;
import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.dto.CreateOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
@Tag(name = "Order API", description = "Endpoints for managing orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
      summary = "Create a new order",
      description = "Creates a new order for a product and quantity")
  @ApiResponse(responseCode = "201", description = "Order successfully created")
  public Order create(@Validated @RequestBody CreateOrderRequest req) {
    log.info("Creating order: {}", req);
    return orderService.create(req.product, req.quantity);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get an order by ID")
  @ApiResponse(responseCode = "200", description = "Order found")
  @ApiResponse(responseCode = "404", description = "Order not found")
  public Order get(
      @Parameter(description = "ID of the order to be retrieved") @PathVariable String id) {
    log.info("Getting order with id {}", id);
    return orderService.get(id);
  }

  @GetMapping
  @Operation(summary = "Get all orders")
  public List<Order> getAll() {
    log.info("Getting all orders");
    return orderService.getAll();
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Delete an order by ID")
  @ApiResponse(responseCode = "204", description = "Order successfully deleted")
  public void delete(
      @Parameter(description = "ID of the order to be deleted") @PathVariable String id) {
    log.info("Deleting order with id {}", id);
    orderService.delete(id);
  }
}
