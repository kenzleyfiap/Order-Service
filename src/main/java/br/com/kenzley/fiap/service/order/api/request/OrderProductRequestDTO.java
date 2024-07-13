package br.com.kenzley.fiap.service.order.api.request;

import br.com.kenzley.fiap.service.order.client.product.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderProductRequestDTO {
    private String productId;
    private String name;
    private BigDecimal price;
    private String information;
    private CategoryDTO category;
}
