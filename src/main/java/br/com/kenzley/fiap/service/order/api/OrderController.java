package br.com.kenzley.fiap.service.order.api;

import br.com.kenzley.fiap.service.order.api.request.OrderRequestDTO;
import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.business.OrderService;
import br.com.kenzley.fiap.service.order.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}/{statusPayment}")
    public void getBackUrlPaymentByIdOrderAndStatusPayment(@PathVariable Long id, @PathVariable String statusPayment) {
        orderService.updateStatusPaymentByOrderId(id, statusPayment);
    }
    @GetMapping("/kitchen")
    public List<OrderResponseDTO> findOrdersByStatusReceivedOrReady() {
        return orderService.findOrdersByStatusReceivedOrReady();
    }

    @GetMapping("/status/{statusOrder}")
    public List<OrderResponseDTO> findByOrderStatus(@PathVariable("statusOrder") OrderStatus statusOrder) {
        return orderService.findByOrderStatus(statusOrder);
    }

    @PutMapping("/{id}/{statusOrder}")
    public void updateStatusOrder(@PathVariable Long id, @PathVariable String statusOrder) {
        orderService.updateOrder(id, statusOrder);
    }
}
