package br.com.kenzley.fiap.service.order.client.payment;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PaymentProductRequestDTO {
    private String productId;
    private String productName;
    private String productDescription;
    private String categoryProduct;
    private Integer quantityProduct;
    private BigDecimal productPrice;
}
