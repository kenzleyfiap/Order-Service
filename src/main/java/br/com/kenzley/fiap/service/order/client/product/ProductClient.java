package br.com.kenzley.fiap.service.order.client.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product", url = "${feign.client.config.product.url}")
public interface ProductClient {

    @GetMapping("/{id}")
    Product getProductById(@PathVariable String id);
}
