-- Delete data first
set foreign_key_checks = 0;

delete from state;
delete from city;
delete from cuisine;
delete from restaurant;
delete from product;
delete from payment_method;
delete from restaurant_payment_method;
delete from permission;
delete from family;
delete from family_permission;
delete from user;
delete from user_group;
DELETE FROM restaurant_user_manager;

set foreign_key_checks = 1;

-- State
INSERT INTO state (id, name) VALUES (1, 'São Paulo');
INSERT INTO state (id, name) VALUES (2, 'Rondônia');

-- City 
INSERT INTO city (id, name, state_id) VALUES (1, 'São Paulo', 1);
INSERT INTO city (id, name, state_id) VALUES (2, 'São Caetano do Sul', 1);
INSERT INTO city (id, name, state_id) VALUES (3, 'Porto Velho', 2);

-- Cuisine
INSERT INTO cuisine (id, name) VALUES (1, 'Italiana');
INSERT INTO cuisine (id, name) VALUES (2, 'Brasileira');

-- Restaurant
INSERT INTO restaurant (id, freight_rate, name, registration_date, update_date, address_complement, address_district, address_number, address_street, address_zipcode, address_city_id, cuisine_id, active, open_status) VALUES (1, 14.9, 'Pecorino', '2022-05-31 12:00:00', '2022-05-31 12:00:00', 'Shopping Center 3' ,'Cerqueira César' ,'2064' ,'Av. Paulista' ,'01310-928 ' , 1, 1, 1, 1);
INSERT INTO restaurant (id, freight_rate, name, registration_date, update_date, address_complement, address_district, address_number, address_street, address_zipcode, address_city_id, cuisine_id, active, open_status) VALUES (2, 0.0, 'Brasil a Gosto', '2022-05-31 12:00:00', '2022-05-31 12:00:00', '' ,'Jardim Paulista' ,'70' ,'Rua Professor Azevedo Amaral' ,'01409-030' , 1, 2, 0, 0);
INSERT INTO restaurant (id, freight_rate, name, registration_date, update_date, address_complement, address_district, address_number, address_street, address_zipcode, address_city_id, cuisine_id, active, open_status) VALUES (3, 25.0, 'San Gennaro', '2022-05-31 12:00:00', '2022-06-01 10:00:00', 'Caiari' ,'' ,'568' ,'Rua Duque de Caxias' ,'76801-170' , 2, 1, 1, 0);

-- Product
INSERT INTO product (id, active, description, name, price, restaurant_id) VALUES (1, 1, 'Queijo coalho na chapa com melaço e pesto de cheiro-verde', 'Queijo coalho', 30.9, 2);
INSERT INTO product (id, active, description, name, price, restaurant_id) VALUES (2, 1, 'Bolinhos de risoto recheados com queijo mussarela', 'Arancini', 39.0, 1);
INSERT INTO product (id, active, description, name, price, restaurant_id) VALUES (3, 1, 'Polpettone recheado de mussarela, coberto com molho de tomates e queijo parmesão, servido com linguine na manteiga', 'Polpettone alla parmigiana com linguine', 125.0, 1);
INSERT INTO product (id, active, description, name, price, restaurant_id) VALUES (4, 1, 'Camarão frito no azeito e alho', 'Camarão alho e óleo', 85.0, 3);
INSERT INTO product (id, active, description, name, price, restaurant_id) VALUES (5, 1, 'Molho de tomate, mussarela, catupiry, provolone e parmesão', 'Quattro Formaggi', 74.0, 3);

-- Payment Methods
INSERT INTO payment_method (id, description) VALUES (1, 'Dinheiro');
INSERT INTO payment_method (id, description) VALUES (2, 'Pix');
INSERT INTO payment_method (id, description) VALUES (3, 'Cartão de débito');
INSERT INTO payment_method (id, description) VALUES (4, 'Cartão de crédito');

-- Restaurant Payment Methods
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, '1');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, '2');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, '3');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, '4');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (2, '2');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (2, '3');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (3, '1');
INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (3, '2');

-- Permission
INSERT INTO permission (id, name, description) VALUES (1, 'CONSULTA', 'Permite apenas a consulta às cozinhas');
INSERT INTO permission (id, name, description) VALUES (2, 'EDIÇÃO', 'Permite a edição das cozinhas');

-- Family (Group)
INSERT INTO family (id, name) VALUES (1, 'Normal');
INSERT INTO family (id, name) VALUES (2, 'Super');

-- Group Permission
INSERT INTO family_permission (family_id, permission_id) VALUES (1, '1');
INSERT INTO family_permission (family_id, permission_id) VALUES (2, '1');
INSERT INTO family_permission (family_id, permission_id) VALUES (2, '2');

-- Users
INSERT INTO user (id, name, email, password, registration_date) VALUES (1, 'Gabriel García Márquez', 'gabriel.garcia.marquez@hotmail.com', '123456', '2022-05-31 12:00:00');
INSERT INTO user (id, name, email, password, registration_date) VALUES (2, 'José Saramago', 'jose.saramago@gmail.com', '123456', '2022-05-31 12:00:00');
INSERT INTO user (id, name, email, password, registration_date) VALUES (3, 'Jane Austen', 'austen.jane@gmail.com', '123456', '2022-05-31 12:00:00');
INSERT INTO user (id, name, email, password, registration_date) VALUES (4, 'Virginia Woolf', 'wolf.virginia@gmail.com', '123456', '2022-05-31 12:00:00');
INSERT INTO user (id, name, email, password, registration_date) VALUES (5, 'Allen de Lima Vieira', 'allenvieira96@gmail.com', '123456', '2022-05-31 12:00:00');

-- User Group
INSERT INTO user_group (user_id, group_id) VALUES (1, 1);
INSERT INTO user_group (user_id, group_id) VALUES (2, 1);
INSERT INTO user_group (user_id, group_id) VALUES (3, 1);
INSERT INTO user_group (user_id, group_id) VALUES (4, 1);
INSERT INTO user_group (user_id, group_id) VALUES (5, 1);
INSERT INTO user_group (user_id, group_id) VALUES (5, 2);

-- Restaraunt Managers
INSERT INTO restaurant_user_manager (restaurant_id, user_id) VALUES (3, 5);
INSERT INTO restaurant_user_manager (restaurant_id, user_id) VALUES (1, 1);
INSERT INTO restaurant_user_manager (restaurant_id, user_id) VALUES (2, 1);
