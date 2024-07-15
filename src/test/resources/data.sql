-- Insere dados na tabela customer
INSERT INTO customer (name, cpf) VALUES ('Nome do Cliente', '12345678901');

-- Insere dados na tabela payment
INSERT INTO payment (amount, status) VALUES (24.00, 'Pago');

-- Insere dados na tabela orders
INSERT INTO orders (customer_id, date_time_order, payment_id, total_amount)
VALUES (1, CURRENT_TIMESTAMP, 1, 24.00);