package br.com.kenzley.fiap.service.order.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment")
@Entity
public class PaymentOrderEntity {

    @Id
    private Long id;
    private String status;
    private BigDecimal amount;
}
