package br.com.kenzley.fiap.service.order.client.payment;

import br.com.kenzley.fiap.service.order.api.request.CustomerRequestDTO;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PaymentRequestDTO {
    private Long idOrder;
    private List<PaymentProductRequestDTO> products;

    private CustomerRequestDTO customer;

}
