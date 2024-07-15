package br.com.kenzley.fiap.service.order.client.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment", url = "${feign.client.config.payment.url}")
public interface PaymentClient {

    @PostMapping
    PaymentResponseDTO createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO);

}
