package br.com.kenzley.fiap.service.order.business;

import br.com.kenzley.fiap.service.order.api.converter.OrderMapper;
import br.com.kenzley.fiap.service.order.api.request.OrderRequestDTO;
import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.client.product.Product;
import br.com.kenzley.fiap.service.order.client.product.ProductClient;
import br.com.kenzley.fiap.service.order.enums.OrderStatus;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderEntity;
import br.com.kenzley.fiap.service.order.infrastructure.entity.OrderProductEntity;
import br.com.kenzley.fiap.service.order.infrastructure.entity.PaymentOrderEntity;
import br.com.kenzley.fiap.service.order.infrastructure.exceptions.NotFoundException;
import br.com.kenzley.fiap.service.order.infrastructure.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        var order = orderMapper.orderRequestToOrder(orderRequestDTO);

        List<OrderProductEntity> orderProducts = orderRequestDTO.getProductsId().stream()
                .map(productId -> {
                    Product product = productClient.getProductById(productId);
                    OrderProductEntity orderProduct = new OrderProductEntity();
                    orderProduct.setProductId(product.id());
                    orderProduct.setName(product.name());
                    orderProduct.setPrice(product.price());
                    orderProduct.setInformation(product.information());
                    orderProduct.setCategory(product.category().name());
                    return orderProduct;
                })
                .toList();

        BigDecimal totalAmount = orderProducts.stream()
                .map(OrderProductEntity::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setDateTimeOrder(LocalDateTime.now());
        order.setProducts(orderProducts);
        order.setTotalAmount(totalAmount);

        PaymentOrderEntity payment = new PaymentOrderEntity();
        payment.setStatus("PENDING");
        payment.setAmount(totalAmount);

        order.setPayment(payment);
        order.setOrderStatus(OrderStatus.RECEIVED);

        var savedOrder = orderRepository.save(order);
        return orderMapper.orderEntityToOrderResponse(savedOrder);
    }

    public OrderResponseDTO getOrderById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        return orderMapper.orderEntityToOrderResponse(order);
    }

    public List<OrderResponseDTO> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::orderEntityToOrderResponse)
                .toList();
    }

    public void updateStatusPaymentByOrderId(Long id, String statusPayment) {
        getOrderById(id);

        Optional<OrderEntity> order = orderRepository.findById(id);
        order.ifPresent(o -> {
            o.getPayment().setStatus(statusPayment);
            orderRepository.save(o);
        } );
    }

    public List<OrderResponseDTO> findOrdersByStatusReceivedOrReady() {
        var byOrderStatus = orderRepository.findByOrderStatus(OrderStatus.RECEIVED, OrderStatus.READY);
        return byOrderStatus.stream()
                .map(orderMapper::orderEntityToOrderResponse)
                .toList();
    }

    public void updateOrder(Long id, String statusOrder) {
        getOrderById(id);
        var order = orderRepository.findById(id);
        order.ifPresent(o -> {
                         o.setOrderStatus(OrderStatus.fromString(statusOrder));
                        orderRepository.save(o);
        });
    }

    public List<OrderResponseDTO> findByOrderStatus(OrderStatus orderStatus) {
        var order = orderRepository.findByOrderStatus(orderStatus);
        return order.stream()
                .map(orderMapper::orderEntityToOrderResponse)
                .toList();
    }
}
