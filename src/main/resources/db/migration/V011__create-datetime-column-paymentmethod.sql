ALTER TABLE payment_method
    ADD update_date DATETIME NULL;

UPDATE payment_method
SET    update_date = utc_timestamp;

ALTER TABLE payment_method
    modify update_date DATETIME NOT NULL;