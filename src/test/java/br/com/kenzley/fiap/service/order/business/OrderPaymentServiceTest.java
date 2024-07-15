package br.com.kenzley.fiap.service.order.business;

import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.client.payment.PaymentClient;
import br.com.kenzley.fiap.service.order.client.payment.PaymentRequestDTO;
import br.com.kenzley.fiap.service.order.client.payment.PaymentResponseDTO;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import br.com.kenzley.fiap.service.order.utils.OrderHelper;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class OrderPaymentServiceTest {

    @Mock
    private OrderService orderService;
    @Mock
    private PaymentClient paymentClient;

    private OrderPaymentService orderPaymentService;
    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        orderPaymentService = new OrderPaymentService(
                orderService,
                paymentClient
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    void mustAllowCreatePayment() {

        // Arrange
        var order = OrderHelper.gerarOrderEntity();
        var orderResponse = OrderHelper.gerarOrderResponse();
        order.setId(1L);

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setInitPoint("teste");

        when(orderService.getOrderById(1L)).thenReturn(orderResponse);
        when(paymentClient.createPayment(any(PaymentRequestDTO.class))).thenReturn(paymentResponseDTO);


        // Act
        var orderPaymentRegistred = orderPaymentService.createPayment(1L);

        // Assert
        assertThat(orderPaymentRegistred).isInstanceOf(PaymentResponseDTO.class).isNotNull();
    }

}