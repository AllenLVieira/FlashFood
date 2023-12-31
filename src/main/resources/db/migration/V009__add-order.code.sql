ALTER TABLE
    delivery_order
ADD
    order_code VARCHAR(36) NOT NULL
AFTER
    id;

UPDATE
    delivery_order
SET
    order_code = uuid();

ALTER TABLE
    delivery_order
ADD
    CONSTRAINT uk_order_code UNIQUE (order_code);