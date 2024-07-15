package br.com.kenzley.fiap.service.order.enums;

import br.com.kenzley.fiap.service.order.infrastructure.exceptions.NotFoundException;

public enum OrderStatus {
    RECEIVED("RECEIVED"),
    IN_PREPARATION("IN_PREPARATION"),
    READY("READY"),
    FINISHED("FINISHED"),
    CANCELED("CANCELED");
    private String statusOrder;

    OrderStatus(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public static OrderStatus fromString(String text) throws NotFoundException {
        for(OrderStatus orderStatus : OrderStatus.values()) {
            if(orderStatus.statusOrder.equals(text.toUpperCase())) {
                return orderStatus;
            }
        }
        throw new NotFoundException("Status de order não encontrado: " + text);
    }
}