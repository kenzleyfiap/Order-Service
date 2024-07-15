package br.com.kenzley.fiap.service.order.business;

import br.com.kenzley.fiap.service.order.api.converter.OrderMapper;
import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.client.product.ProductClient;
import br.com.kenzley.fiap.service.order.enums.OrderStatus;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import br.com.kenzley.fiap.service.order.infrastructure.exceptions.NotFoundException;
import br.com.kenzley.fiap.service.order.infrastructure.repository.OrderRepository;
import br.com.kenzley.fiap.service.order.infrastructure.repository.PaymentOrderRepository;
import br.com.kenzley.fiap.service.order.utils.OrderHelper;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Mock
    private ProductClient productClient;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private PaymentOrderRepository paymentOrderRepository;
    private OrderService orderService;
    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        orderService = new OrderService(
                productClient,
                orderRepository,
                orderMapper
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    void mustAllowRegisterOrder() {

        // Arrange
        var order = OrderHelper.gerarOrderRequestDTO();
        when(orderRepository.save(any(OrderEntity.class))).thenAnswer(i -> i.getArgument(0));
        when(productClient.getProductById(any(String.class))).thenReturn(OrderHelper.gerarProduct());
        when(orderMapper.orderRequestToOrder(order)).thenReturn(OrderHelper.gerarOrderEntity());
        when(orderMapper.orderEntityToOrderResponse(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderResponse());


        // Act
        var orderRegistred = orderService.createOrder(order);

        // Assert
        assertThat(orderRegistred).isInstanceOf(OrderResponseDTO.class).isNotNull();
    }

    @Test
    void mustAllowFindOrder() throws NotFoundException {
        // Arrange
        var orderEntity = OrderHelper.gerarOrderEntity();
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of((orderEntity)));
        when(orderMapper.orderEntityToOrderResponse(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderResponse());

        // Act
        var productReceived = orderService.getOrderById(orderEntity.getId());

        // Assert
        Assertions.assertThat(productReceived).isNotNull();
        verify(orderRepository, times(1)).findById(any(Long.class));

    }

    @Test
    void mustAllowFindOrdersByStatusReceivedOrReady() throws NotFoundException {
        // Arrange
        var orderEntity = OrderHelper.gerarOrderEntity();
        when(orderRepository.findByOrderStatus(OrderStatus.RECEIVED, OrderStatus.READY)).thenReturn(List.of(orderEntity));
        when(orderMapper.orderEntityToOrderResponse(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderResponse());

        // Act
        var orderReceived = orderService.findOrdersByStatusReceivedOrReady();

        // Assert
        Assertions.assertThat(orderReceived).isNotNull();
        verify(orderRepository, times(1)).findByOrderStatus(OrderStatus.RECEIVED, OrderStatus.READY);

    }

    @Test
    void mustAllowUpdateStatusPaymentByOrderId() throws NotFoundException {
        // Arrange
        var orderEntity = OrderHelper.gerarOrderEntity();
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of((orderEntity)));
        when(orderMapper.orderEntityToOrderResponse(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderResponse());

        // Act
         orderService.updateStatusPaymentByOrderId(1L, "Approved");

        // Assert
        verify(orderRepository, times(1)).save(any(OrderEntity.class));

    }

    @Test
    void mustAllowUpdateOrder() throws NotFoundException {
        // Arrange
        var orderEntity = OrderHelper.gerarOrderEntity();
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of((orderEntity)));
        when(orderMapper.orderEntityToOrderResponse(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderResponse());

        // Act
        orderService.updateOrder(1L, OrderStatus.FINISHED.toString());

        // Assert
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void mustAllowFindByOrderStatus() throws NotFoundException {
        // Arrange
        var orderEntity = OrderHelper.gerarOrderEntity();
        when(orderRepository.findByOrderStatus(OrderStatus.RECEIVED)).thenReturn(List.of((orderEntity)));
        when(orderMapper.orderEntityToOrderResponse(any(OrderEntity.class))).thenReturn(OrderHelper.gerarOrderResponse());

        // Act
        List<OrderResponseDTO> orderReceived = orderService.findByOrderStatus(OrderStatus.RECEIVED);

        // Assert
        Assertions.assertThat(orderReceived).isNotNull();
        verify(orderRepository, times(1)).findByOrderStatus(any(OrderStatus.class));
    }



    @Test
    void mustGenerateExceptionWhenIdDoesNotExist() {

        when(orderRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(10L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order not found");

        verify(orderRepository, times(1)).findById(10L);
    }

    @Test
    void mustAllowListOrders() {
        var order1 = OrderHelper.gerarOrderEntity();
        var order2 = OrderHelper.gerarOrderEntity();

        List<OrderEntity> listProducts = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(listProducts);

        // Act
        var productsReceived = orderService.getAllOrders();

        // Assert
        Assertions.assertThat(productsReceived).hasSize(2);

        verify(orderRepository, times(1)).findAll();
    }

}