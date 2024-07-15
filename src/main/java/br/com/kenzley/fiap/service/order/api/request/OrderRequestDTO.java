package br.com.kenzley.fiap.service.order.api.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class OrderRequestDTO {

    private CustomerRequestDTO customer;
    private List<String> productsId;
}
