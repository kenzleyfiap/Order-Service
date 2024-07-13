package br.com.kenzley.fiap.service.order.api.converter;

import br.com.kenzley.fiap.service.order.api.request.OrderRequestDTO;
import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "products", target = "products")
    OrderResponseDTO orderEntityToOrderResponse(OrderEntity order);

    OrderEntity orderRequestToOrder(OrderRequestDTO orderRequest);
}
