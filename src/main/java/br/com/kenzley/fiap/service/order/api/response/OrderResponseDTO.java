package br.com.kenzley.fiap.service.order.api.response;

import br.com.kenzley.fiap.service.order.api.request.CustomerRequestDTO;
import br.com.kenzley.fiap.service.order.api.request.OrderProductRequestDTO;
import br.com.kenzley.fiap.service.order.api.request.PaymentOrderDTO;
import br.com.kenzley.fiap.service.order.client.product.OrderProductResponseDTO;
import br.com.kenzley.fiap.service.order.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderResponseDTO {

    private Long id;
    private CustomerRequestDTO customer;
    private List<OrderProductResponseDTO> products;
    private LocalDateTime dateTimeOrder;
    private PaymentOrderDTO payment;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
}
