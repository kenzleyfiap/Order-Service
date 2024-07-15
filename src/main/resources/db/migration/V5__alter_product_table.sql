ALTER TABLE product ADD COLUMN order_id BIGINT;
ALTER TABLE product ADD FOREIGN KEY (order_id) REFERENCES orders(id);