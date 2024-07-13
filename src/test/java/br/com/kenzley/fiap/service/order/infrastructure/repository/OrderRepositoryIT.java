package br.com.kenzley.fiap.service.order.infrastructure.repository;

import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;

import static br.com.kenzley.fiap.service.order.utils.OrderHelper.gerarOrderEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class OrderRepositoryIT {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void mustAllowRegisterOrder() {

        // Arrange
        var order = gerarOrderEntity();

        // Act
        var orderRegistred = orderRepository.save(order);

        // Assert

        assertThat(orderRegistred)
                .isNotNull()
                .isInstanceOf(OrderEntity.class);

        assertThat(orderRegistred.getId()).isEqualTo(order.getId());
        assertThat(orderRegistred.getTotalAmount()).isEqualTo(order.getTotalAmount());
        assertThat(orderRegistred.getDateTimeOrder()).isEqualTo(order.getDateTimeOrder());
    }

    @Test
    void mustAllowFindOrder() {

        // Act
        Optional<OrderEntity> orderReceivedOptional = orderRepository.findById(1L);

        // Assert
        assertThat(orderReceivedOptional).isPresent();

        orderReceivedOptional.ifPresent(productReceived -> {
            assertThat(productReceived.getId()).isEqualTo(1L);
        });
    }

    @Test
    void mustAllowDeleteOrder() {

        // Act
        orderRepository.deleteById(1L);
        Optional<OrderEntity> orderReceivedOptional = orderRepository.findById(1L);

        // Assert
        assertThat(orderReceivedOptional).isEmpty();

    }

    @Test
    void mustAllowFindAllOrder() {
        //Act
        var orderListReceived = orderRepository.findAll();

        //Assert
        assertThat(orderListReceived)
                .hasSize(1);

        assertThat(orderListReceived).hasSizeGreaterThan(0);
    }


}