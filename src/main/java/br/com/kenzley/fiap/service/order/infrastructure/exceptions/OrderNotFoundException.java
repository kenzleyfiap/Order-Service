package br.com.kenzley.fiap.service.order.infrastructure.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
