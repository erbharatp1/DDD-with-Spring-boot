package com.bharat.ddd.order.dto;

public class CreateOrderRequest {
  //   @NotBlank(message = "Product name is required")
  public String product;

  ///  @Min(value = 1, message = "Quantity must be at least 1")
  public int quantity;
}
