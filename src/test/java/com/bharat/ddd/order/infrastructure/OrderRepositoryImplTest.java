package com.bharat.ddd.order.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryImplTest {

  @Mock private OrderJpaRepository orderJpaRepository;

  @InjectMocks private OrderRepositoryImpl orderRepository;

  @Test
  void shouldSaveOrderWhenValidOrderIsProvided() {
    String orderId = "test-id";
    String product = "Test Product";
    int quantity = 10;
    Order order = new Order(new OrderId(orderId), product, quantity);
    OrderEntity entity = new OrderEntity(orderId, product, quantity);

    when(orderJpaRepository.save(any(OrderEntity.class))).thenReturn(entity);

    Order savedOrder = orderRepository.save(order);

    assertNotNull(savedOrder);
    assertEquals(orderId, savedOrder.getId().getValue());
    verify(orderJpaRepository, times(1)).save(any(OrderEntity.class));
  }

  @Test
  void shouldFindOrderByIdWhenOrderExists() {
    String orderId = "test-id";
    OrderEntity entity = new OrderEntity(orderId, "Test Product", 10);

    when(orderJpaRepository.findById(orderId)).thenReturn(Optional.of(entity));

    Optional<Order> foundOrder = orderRepository.findById(orderId);

    assertTrue(foundOrder.isPresent());
    assertEquals(orderId, foundOrder.get().getId().getValue());
    verify(orderJpaRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldReturnEmptyWhenOrderDoesNotExist() {
    String orderId = "test-id";

    when(orderJpaRepository.findById(orderId)).thenReturn(Optional.empty());

    Optional<Order> foundOrder = orderRepository.findById(orderId);

    assertTrue(foundOrder.isEmpty());
    verify(orderJpaRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldFindAllOrdersWhenOrdersExist() {
    List<OrderEntity> entities =
        List.of(new OrderEntity("id1", "Product 1", 5), new OrderEntity("id2", "Product 2", 10));

    when(orderJpaRepository.findAll()).thenReturn(entities);

    List<Order> allOrders = orderRepository.findAll();

    assertNotNull(allOrders);
    assertEquals(2, allOrders.size());
    verify(orderJpaRepository, times(1)).findAll();
  }

  @Test
  void shouldDeleteOrderByIdWhenOrderExists() {
    String orderId = "test-id";

    doNothing().when(orderJpaRepository).deleteById(orderId);

    orderRepository.deleteById(orderId);

    verify(orderJpaRepository, times(1)).deleteById(orderId);
  }
}
