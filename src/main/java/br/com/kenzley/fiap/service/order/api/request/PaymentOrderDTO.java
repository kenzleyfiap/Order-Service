package br.com.kenzley.fiap.service.order.api.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PaymentOrderDTO {
    private Long id;
    private String status;
    private BigDecimal amount;
}
