package br.com.kenzley.fiap.service.order.client.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderProductResponseDTO {
    private String productId;
    private String name;
    private BigDecimal price;
    private String information;
    private String category;
}
