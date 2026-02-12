package com.bharat.ddd.order.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;

public class OrderId {
  private final String value;

  public OrderId() {
    this.value = UUID.randomUUID().toString();
  }

  public OrderId(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
