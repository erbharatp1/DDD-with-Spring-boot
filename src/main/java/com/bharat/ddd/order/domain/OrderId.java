package com.bharat.ddd.order.domain;

import java.util.UUID;

public class OrderId {
  private final String value;

  public OrderId() {
    this.value = UUID.randomUUID().toString();
  }

  public OrderId(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
