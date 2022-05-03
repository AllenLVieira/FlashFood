ALTER TABLE
 restaurant
ADD
 open_status TINYINT(1) NOT NULL;

UPDATE
 restaurant
SET
 open_status = false;