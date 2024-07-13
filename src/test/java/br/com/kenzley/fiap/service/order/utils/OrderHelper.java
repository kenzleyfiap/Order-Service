package br.com.kenzley.fiap.service.order.utils;

import br.com.kenzley.fiap.service.order.api.request.CustomerRequestDTO;
import br.com.kenzley.fiap.service.order.api.request.OrderRequestDTO;
import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.client.product.CategoryDTO;
import br.com.kenzley.fiap.service.order.client.product.Product;
import br.com.kenzley.fiap.service.order.infrastructure.entity.CustomerEntity;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderProductEntity;
import br.com.kenzley.fiap.service.order.infrastructure.entity.PaymentOrderEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderHelper {

    public static OrderEntity gerarOrderEntity() {
        return OrderEntity.builder()
                .id(1L)
                .customer(gerarCustomerEntity())
                .dateTimeOrder(LocalDateTime.now())
                .payment(gerarPaymentEntity())
                .products(List.of(gerarProductEntity()))
                .totalAmount(new BigDecimal("30.0"))
                .build();
    }

    public static OrderRequestDTO gerarOrderRequestDTO() {
        return OrderRequestDTO.builder()
                .customer(gerarCustomerRequestDTO())
                .productsId(List.of("19761bff-a379-45dd-8db5-7b4670f7e0b9"))
                .build();
    }

    public static CustomerRequestDTO gerarCustomerRequestDTO() {
        return CustomerRequestDTO.builder()
                .name("Teste")
                .cpf("11111111111")
                .build();
    }

    public static CustomerEntity gerarCustomerEntity() {
        return CustomerEntity.builder()
                .name("Teste")
                .build();
    }

    public static PaymentOrderEntity gerarPaymentEntity() {
        return PaymentOrderEntity.builder()
                .id(1L)
                .status("PENDING")
                .amount(new BigDecimal("33.0"))
                .build();
    }
    public static OrderProductEntity gerarProductEntity() {
        return OrderProductEntity.builder()
                .productId("a0d94841-8ac4-4346-af6d-dbb82bd316cc")
                .price(new BigDecimal("12.0"))
                .information("Information")
                .name("Hamburguer")
                .build();
    }

    public static Product gerarProduct() {
        Product product = new Product("a0d94841-8ac4-4346-af6d-dbb82bd316cc",
                "Hamburguer",
                new BigDecimal("12.0"),
                "Information",
                new CategoryDTO("Hambúrguers"));
        return product;
    }

    public static OrderResponseDTO gerarOrderResponse() {
        return OrderResponseDTO.builder()
                .dateTimeOrder(gerarOrderEntity().getDateTimeOrder())
                .customer(gerarCustomerRequestDTO())
                .id(gerarOrderEntity().getId())
                .build();
    }
}
