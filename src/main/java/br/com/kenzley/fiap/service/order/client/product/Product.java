package br.com.kenzley.fiap.service.order.client.product;

import java.math.BigDecimal;

public record Product(
        String id,
        String name,
        BigDecimal price,
        String information,
        CategoryDTO category
) {
}
