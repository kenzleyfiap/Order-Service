package br.com.kenzley.fiap.service.order.infrastructure.repository;

import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
