package com.bharat.ddd.order.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import com.bharat.ddd.order.domain.OrderNotFoundException;
import com.bharat.ddd.order.domain.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceTest {

  @Mock private OrderRepository orderRepository;

  @InjectMocks private OrderService orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateOrderWhenValidDataIsProvided() {
    String product = "Test Product";
    int quantity = 10;
    Order order = new Order(new OrderId(), product, quantity);

    when(orderRepository.save(any(Order.class))).thenReturn(order);

    Order createdOrder = orderService.create(product, quantity);

    assertNotNull(createdOrder);
    assertEquals(product, createdOrder.getProduct());
    assertEquals(quantity, createdOrder.getQuantity());
    verify(orderRepository, times(1)).save(any(Order.class));
  }

  @Test
  void shouldGetOrderWhenOrderExists() {
    String orderId = "test-id";
    Order order = new Order(new OrderId(orderId), "Test Product", 10);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

    Order foundOrder = orderService.get(orderId);

    assertNotNull(foundOrder);
    assertEquals(orderId, foundOrder.getId().getValue());
    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldThrowExceptionWhenGettingNonExistentOrder() {
    String orderId = "test-id";

    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    assertThrows(OrderNotFoundException.class, () -> orderService.get(orderId));
    verify(orderRepository, times(1)).findById(orderId);
  }

  @Test
  void shouldGetAllOrdersWhenOrdersExist() {
    List<Order> orders =
        List.of(
            new Order(new OrderId(), "Product 1", 5), new Order(new OrderId(), "Product 2", 10));

    when(orderRepository.findAll()).thenReturn(orders);

    List<Order> allOrders = orderService.getAll();

    assertNotNull(allOrders);
    assertEquals(2, allOrders.size());
    verify(orderRepository, times(1)).findAll();
  }

  @Test
  void shouldDeleteOrderWhenOrderExists() {
    String orderId = "test-id";
    Order order = new Order(new OrderId(orderId), "Test Product", 10);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    doNothing().when(orderRepository).deleteById(orderId);

    orderService.delete(orderId);

    verify(orderRepository, times(1)).deleteById(orderId);
  }

  @Test
  void shouldThrowExceptionWhenDeletingNonExistentOrder() {
    String orderId = "test-id";

    when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

    assertThrows(OrderNotFoundException.class, () -> orderService.delete(orderId));
    verify(orderRepository, never()).deleteById(orderId);
  }
}
