package com.bharat.ddd.order.domain;

public class Order {
  private OrderId id;
  private String product;
  private int quantity;

  public Order(OrderId id, String product, int quantity) {
    this.id = id;
    this.product = product;
    this.quantity = quantity;
  }

  public OrderId getId() {
    return id;
  }

  public String getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }
}
