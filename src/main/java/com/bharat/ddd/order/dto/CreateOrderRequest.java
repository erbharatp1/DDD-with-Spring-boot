package com.bharat.ddd.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for creating a new order")
public class CreateOrderRequest {

  @Schema(description = "Name of the product", example = "Laptop")
  public String product;

  @Schema(description = "Quantity of the product", example = "1")
  public int quantity;
}
