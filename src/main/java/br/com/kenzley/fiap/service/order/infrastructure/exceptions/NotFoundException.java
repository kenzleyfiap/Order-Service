package br.com.kenzley.fiap.service.order.infrastructure.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
