package br.com.kenzley.fiap.service.order.api;

import br.com.kenzley.fiap.service.order.api.request.OrderRequestDTO;
import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.business.OrderService;
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
}
