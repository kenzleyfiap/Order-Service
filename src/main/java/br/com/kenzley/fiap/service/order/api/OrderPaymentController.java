package br.com.kenzley.fiap.service.order.api;

import br.com.kenzley.fiap.service.order.business.OrderPaymentService;
import br.com.kenzley.fiap.service.order.client.payment.PaymentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/payment")
@RequiredArgsConstructor
public class OrderPaymentController {

    private final OrderPaymentService orderPaymentService;

    @Operation(
            summary = "Create Payment by OrderId",
            description = "Create payment in payment-service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("/{id}")
    public PaymentResponseDTO createPaymentByOrderId(@PathVariable Long id) {
        return orderPaymentService.createPayment(id);
    }
}
