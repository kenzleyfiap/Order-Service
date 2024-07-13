package br.com.kenzley.fiap.service.order.infrastructure.repository;

import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static br.com.kenzley.fiap.service.order.utils.OrderHelper.gerarOrderEntity;
import static br.com.kenzley.fiap.service.order.utils.OrderHelper.gerarProductEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class OrderRepositoryTest {
    @Mock
    private OrderRepository orderRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void mustAllowRegisterOrder() {
        // Arrange
        var order = gerarOrderEntity();
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(order);

        // Act
        var orderRegistred = orderRepository.save(order);

        // Assert

        assertThat(orderRegistred)
                .isNotNull()
                .isEqualTo(order);

        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }

    @Test
    void mustAllowFindOrder() {

        // Arrange

        var order = gerarOrderEntity();

        when(orderRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(order));

        // Act
        var orderReceivedOptional = orderRepository.findById(order.getId());

        // Assert

        assertThat(orderReceivedOptional)
                .isPresent()
                .containsSame(order);


        orderReceivedOptional.ifPresent(orderReceived -> {
            assertThat(orderReceived.getId()).isEqualTo(order.getId());
            assertThat(orderReceived.getDateTimeOrder()).isEqualTo(order.getDateTimeOrder());
        });

        verify(orderRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void mustAllowDeleteOrder() {
        //Arrange
        doNothing().when(orderRepository).deleteById(any(Long.class));

        // Act
        orderRepository.deleteById(1L);

        // Assert
        verify(orderRepository, times(1)).deleteById(any(Long.class));

    }

    @Test
    void mustAllowFindAllOrders() {
        var order1 = gerarOrderEntity();
        var order2 = gerarOrderEntity();

        // Arrange
        var orderList = Arrays.asList(
                order1,
                order2
        );

        when(orderRepository.findAll()).thenReturn(orderList);

        //Act
        var orderListReceived = orderRepository.findAll();

        //Assert
        assertThat(orderListReceived)
                .hasSize(2)
                .containsExactlyInAnyOrder(order1, order2);

        verify(orderRepository, times(1)).findAll();
    }


}