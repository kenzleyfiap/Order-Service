package br.com.kenzley.fiap.service.order.infrastructure.repository;

import br.com.kenzley.fiap.service.order.infrastructure.entity.PaymentOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrderEntity, Long> {
}
