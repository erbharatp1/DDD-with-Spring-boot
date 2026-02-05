package com.bharat.ddd.order.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderEntity {

  @Id private String id;
  private String product;
  private int quantity;

  public OrderEntity() {}

  public OrderEntity(String id, String product, int quantity) {
    this.id = id;
    this.product = product;
    this.quantity = quantity;
  }

  public String getId() {
    return id;
  }

  public String getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }
}
