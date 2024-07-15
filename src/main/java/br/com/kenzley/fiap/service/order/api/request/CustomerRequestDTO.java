package br.com.kenzley.fiap.service.order.api.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CustomerRequestDTO {
    private String name;
    private String cpf;
    private String email;
}
