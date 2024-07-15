package br.com.kenzley.fiap.service.order.api;

import br.com.kenzley.fiap.service.order.api.request.OrderRequestDTO;
import br.com.kenzley.fiap.service.order.api.response.OrderResponseDTO;
import br.com.kenzley.fiap.service.order.business.OrderService;
import br.com.kenzley.fiap.service.order.enums.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(
            summary = "Create Order",
            description = "Create a order in data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.createOrder(orderRequestDTO);
    }

    @Operation(
            summary = "Fetch Order by Id",
            description = "fetche one order entities and ther data from data source ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @GetMapping("/{id}")
    public OrderResponseDTO getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }

    @Operation(
            summary = "Fetch all orders",
            description = "fetches all orders entities and ther data from data source ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @Operation(
            summary = "Fetch order by Id and Payment Status",
            description = "fetche one order entities and ther data from data source ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @GetMapping("/{id}/{statusPayment}")
    public void getBackUrlPaymentByIdOrderAndStatusPayment(@PathVariable Long id, @PathVariable String statusPayment) {
        orderService.updateStatusPaymentByOrderId(id, statusPayment);
    }

    @Operation(
            summary = "Fetch Order by status RECEVEID OR READY",
            description = "fetche one order entities and ther data from data source ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @GetMapping("/kitchen")
    public List<OrderResponseDTO> findOrdersByStatusReceivedOrReady() {
        return orderService.findOrdersByStatusReceivedOrReady();
    }

    @Operation(
            summary = "Fetch order by status",
            description = "fetche one product entities and ther data from data source ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "not found")
    })
    @GetMapping("/status/{statusOrder}")
    public List<OrderResponseDTO> findByOrderStatus(@PathVariable("statusOrder") OrderStatus statusOrder) {
        return orderService.findByOrderStatus(statusOrder);
    }
    @Operation(
            summary = "Fetch product by Id and status",
            description = "fetche one order entities and ther data from data source ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "not found")
    })

    @PutMapping("/{id}/{statusOrder}")
    public void updateStatusOrder(@PathVariable Long id, @PathVariable String statusOrder) {
        orderService.updateOrder(id, statusOrder);
    }
}
