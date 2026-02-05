package com.bharat.ddd.order.interfaces;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bharat.ddd.order.application.OrderService;
import com.bharat.ddd.order.domain.Order;
import com.bharat.ddd.order.domain.OrderId;
import com.bharat.ddd.order.dto.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private OrderService orderService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void shouldCreateOrderWhenValidRequestIsProvided() throws Exception {
    CreateOrderRequest request = new CreateOrderRequest();
    request.product = "Test Product";
    request.quantity = 10;

    OrderId orderId = new OrderId("test-id");
    Order order = new Order(orderId, request.product, request.quantity);

    when(orderService.create(request.product, request.quantity)).thenReturn(order);

    mockMvc
        .perform(
            post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id.value").value("test-id"))
        .andExpect(jsonPath("$.product").value("Test Product"))
        .andExpect(jsonPath("$.quantity").value(10));
  }

  @Test
  void shouldGetOrderWhenOrderExists() throws Exception {
    String orderId = "test-id";
    Order order = new Order(new OrderId(orderId), "Test Product", 10);

    when(orderService.get(orderId)).thenReturn(order);

    mockMvc
        .perform(get("/orders/{id}", orderId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id.value").value(orderId))
        .andExpect(jsonPath("$.product").value("Test Product"));
  }

  @Test
  void shouldGetAllOrdersWhenOrdersExist() throws Exception {
    Order order = new Order(new OrderId("test-id"), "Test Product", 10);
    List<Order> orders = Collections.singletonList(order);

    when(orderService.getAll()).thenReturn(orders);

    mockMvc
        .perform(get("/orders"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id.value").value("test-id"))
        .andExpect(jsonPath("$[0].product").value("Test Product"));
  }

  @Test
  void shouldDeleteOrderWhenOrderExists() throws Exception {
    String orderId = "test-id";
    doNothing().when(orderService).delete(orderId);
    mockMvc.perform(delete("/orders/{id}", orderId)).andExpect(status().isNoContent());
  }
}
