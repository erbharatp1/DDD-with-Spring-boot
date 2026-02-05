package com.bharat.ddd.order.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(String id) {
    super("Could not find order with id: " + id);
  }
}
