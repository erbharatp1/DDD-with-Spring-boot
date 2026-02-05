package com.bharat.ddd.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderEntityTest {

  @Test
  void testOrderEntity() {
    String id = "test-id";
    String product = "Test Product";
    int quantity = 10;

    OrderEntity entity = new OrderEntity(id, product, quantity);

    assertEquals(id, entity.getId());
    assertEquals(product, entity.getProduct());
    assertEquals(quantity, entity.getQuantity());
  }

  @Test
  void testOrderEntityDefaultConstructor() {
    OrderEntity entity = new OrderEntity();
    assertNull(entity.getId());
    assertNull(entity.getProduct());
    assertEquals(0, entity.getQuantity());
  }
}
