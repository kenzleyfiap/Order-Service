CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    date_time_order TIMESTAMP,
    payment_id BIGINT,
    total_amount DECIMAL(15, 2),
    order_status VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (payment_id) REFERENCES payment(id)
);