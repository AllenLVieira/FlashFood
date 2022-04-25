set foreign_key_checks = 0;

delete from city;
delete from cuisine;
delete from family_permission;
delete from family;
delete from restaurant_payment_method;
delete from restaurant;
delete from product;
delete from permission;
delete from payment_method;
delete from state;
delete from user;
delete from user_group;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table cuisine auto_increment = 1;
alter table family auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table product auto_increment = 1;
alter table permission auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table state auto_increment = 1;
alter table user auto_increment = 1;

INSERT INTO state (id, name) VALUES (1, 'São Paulo'),(2, 'Bahia'),(3, 'Pernambuco');

INSERT INTO city (id, name, state_id) VALUES (1, 'São Paulo', 1),(2, 'Salvador', 2),(3, 'Recife', 3);

INSERT INTO cuisine (id, name) VALUES (1, 'Italian'),(2, 'Brazilian');

INSERT INTO restaurant (name, freight_rate, cuisine_id, registration_date,update_date, address_zipcode, address_street, address_number, address_city_id, address_district, address_complement, active) VALUES('Pecorino', 16.9, 1, utc_timestamp, utc_timestamp, '04285-001', 'Rua Elba','909', 1, 'Vila Moinho Velho', 'Casa 2', true);
INSERT INTO restaurant (name, freight_rate, cuisine_id, registration_date,update_date, address_zipcode, address_street, address_number, address_city_id, address_district, address_complement, active) VALUES('Soho', 30, 2, utc_timestamp, utc_timestamp,'40015-160', 'Rua Lafayete Coutinho','1010', 2, 'Bahia Marina', '', true);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Bruschetta', 'Delicious Italian delicacy with melted cheese.', 8, 1,1);
INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Fish Moqueca', 'Moqueca is a unique Brazilian delicacy.', 100, 1,2);

INSERT INTO payment_method (id, description) VALUES (1, 'Credit Card'),(2, 'Debit Card'),(3, 'Money');

INSERT INTO permission (id, name, description) VALUES (1, 'CONSULT', 'Allows you consult cuisines');
INSERT INTO permission (id, name, description) VALUES (2, 'EDIT', 'Allows you to edit cuisines');

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 1),(1, 2),(1, 3),(2, 3);

INSERT INTO family (name) values ('TESTE1'), ('TESTE2'), ('TESTE3'), ('TESTE4');

INSERT INTO user (id, name, email, password, registration_date) values
(1, 'João da Silva', 'joao.ger@gmail.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@gmail.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@gmail.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@gmail.com', '123', utc_timestamp);