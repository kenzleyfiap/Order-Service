package br.com.kenzley.fiap.service.order.api;

import br.com.kenzley.fiap.service.order.api.converter.OrderMapper;
import br.com.kenzley.fiap.service.order.api.request.OrderRequestDTO;
import br.com.kenzley.fiap.service.order.business.OrderService;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import br.com.kenzley.fiap.service.order.infrastructure.exceptions.Handler.GlobalExceptionHandler;
import br.com.kenzley.fiap.service.order.infrastructure.exceptions.OrderNotFoundException;
import br.com.kenzley.fiap.service.order.infrastructure.repository.OrderRepository;
import br.com.kenzley.fiap.service.order.utils.OrderHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {

    private MockMvc mockMvc;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderService orderService;
    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        OrderController orderController = new OrderController(orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .addFilter((request,response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class RegisterOrder {

        @Test
        void mustAllowRegisterOrder() throws Exception {

            // Arrange
            var order = OrderHelper.gerarOrderRequestDTO();
            when(orderService.createOrder(order)).thenReturn(OrderHelper.gerarOrderResponse());
            when(orderMapper.orderEntityToOrderResponse(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderResponse());
            when(orderMapper.orderRequestToOrder(any(OrderRequestDTO.class))).thenReturn(OrderHelper.gerarOrderEntity());
            when(orderRepository.save(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderEntity());

            // Act && Assert
            mockMvc.perform(
                    post("/orders")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(order))
            ).andExpect(status().isOk());

            verify(orderService, times(1)).createOrder(any(OrderRequestDTO.class));

        }

        @Test
        void mustGenerateExceptionWhenRegisterOrderPayloadXML() throws Exception {
            String xmlPayload =
                    "<orderRequestDTO>   <customer>\n" +
                    "      <cpf>11111111111</cpf>\n" +
                    "      <name>teste</name>\n" +
                    "   </customer>\n" +
                    "   <productsId>\n" +
                    "      <element>a0d94841-8ac4-4346-af6d-dbb82bd316cc</element>\n" +
                    "      <element>a0d94841-8ac4-4346-af6d-dbb82bd316cc</element>\n" +
                    "   </productsId>" +
                    "</orderRequestDTO>";

            mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_XML)
                            .content(xmlPayload))
                    .andExpect(status().isUnsupportedMediaType());

            verify(orderService, never()).createOrder(any(OrderRequestDTO.class));
        }
    }

    @Nested
    class FindOrder {

        @Test
        void mustAllowFindOrder() throws Exception {

            var order = OrderHelper.gerarOrderResponse();
            when(orderService.getOrderById(any(Long.class)))
                    .thenReturn(order);

            mockMvc.perform(get("/orders/{id}", 1L)).andExpect(status().isOk());

            verify(orderService, times(1)).getOrderById(any(Long.class));

        }

        @Test
        void mustGenerateExceptionWhenIdNotFound() throws Exception {
            var order = OrderHelper.gerarOrderRequestDTO();

            when(orderService.getOrderById(350L))
                    .thenThrow(new OrderNotFoundException("Order not Found"));

            mockMvc.perform(get("/orders/{id}", 350L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(order)))
                    .andExpect(status().isNotFound());

            verify(orderService, times(1)).getOrderById(any(Long.class));
        }

    }

    @Nested
    class ListOrders {

        @Test
        void mustAllowListOrders() throws Exception {
            var order = OrderHelper.gerarOrderResponse();

            var orders = List.of(order, order);

            when(orderService.getAllOrders()).thenReturn(orders);

            mockMvc.perform(get("/orders"))
                    .andExpect(status().isOk());
        }

    }


    public static String asJsonString(final Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        return objectMapper.writeValueAsString(object);
    }

}