package com.bharat.ddd.order.interfaces;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bharat.ddd.order.dto.CreateOrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/orders.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class OrderControllerIT {

  private static final String EXISTING_ORDER_ID = "11111111-1111-1111-1111-111111111111";
  private static final String NON_EXISTENT_ORDER_ID = "99999999-9999-9999-9999-999999999999";

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Nested
  class GetOrdersTest {

    @Test
    void shouldReturnAllOrdersWhenOrdersExist() throws Exception {
      mockMvc
          .perform(get("/orders"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
          .andExpect(jsonPath("$[*].id.value", notNullValue()))
          .andExpect(jsonPath("$[*].product", hasItems("Laptop", "Smartphone", "Keyboard")));
    }
  }

  @Nested
  class GetOrderByIdTest {

    @Test
    @DisplayName("Should return order when order exists")
    void shouldReturnOrderWhenOrderExists() throws Exception {
      mockMvc
          .perform(get("/orders/{id}", EXISTING_ORDER_ID))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.product", is("Laptop")))
          .andExpect(jsonPath("$.quantity", is(1)));
    }

    @Test
    void shouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
      mockMvc.perform(get("/orders/{id}", NON_EXISTENT_ORDER_ID)).andExpect(status().isNotFound());
    }
  }

  @Nested
  class CreateOrderTest {

    @Test
    void shouldCreateOrderWhenInputIsValid() throws Exception {
      CreateOrderRequest request = new CreateOrderRequest();
      request.product = "New Product";
      request.quantity = 1;

      mockMvc
          .perform(
              post("/orders")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id.value", notNullValue()))
          .andExpect(jsonPath("$.product", is("New Product")));
    }

    @Test
    void shouldReturnBadRequestWhenProductIsBlank() throws Exception {
      CreateOrderRequest request = new CreateOrderRequest();
      request.product = "";
      request.quantity = 1;

      mockMvc
          .perform(
              post("/orders")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenQuantityIsZero() throws Exception {
      CreateOrderRequest request = new CreateOrderRequest();
      request.product = "A Product";
      request.quantity = 0;

      mockMvc
          .perform(
              post("/orders")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isBadRequest());
    }
  }

  @Nested
  class DeleteOrderTest {

    @Test
    void shouldDeleteOrderWhenOrderExists() throws Exception {
      mockMvc.perform(delete("/orders/{id}", EXISTING_ORDER_ID)).andExpect(status().isNoContent());

      mockMvc.perform(get("/orders/{id}", EXISTING_ORDER_ID)).andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentOrder() throws Exception {
      mockMvc
          .perform(delete("/orders/{id}", NON_EXISTENT_ORDER_ID))
          .andExpect(status().isNotFound());
    }
  }
}
