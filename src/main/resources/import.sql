insert into state (id, name) values (1, 'São Paulo');
insert into state (id, name) values (2, 'Bahia');
insert into state (id, name) values (3, 'Pernambuco');

insert into city (id, name, state_id) values (1, 'São Paulo', 1);
insert into city (id, name, state_id) values (2, 'Salvador', 2);
insert into city (id, name, state_id) values (3, 'Recife', 3);

insert into cuisine (id, name) values(1, 'Italian');
insert into cuisine (id, name) values(2, 'Brazilian');

INSERT INTO restaurant (name,freight_rate,cuisine_id,address_zipcode,address_street,address_number,address_city_id,address_district,address_complement) VALUES ('Pecorino',16.9,1,'04285-001','Rua Elba','909',1,'Vila Moinho Velho','Casa 2');
INSERT INTO restaurant (name,freight_rate,cuisine_id,address_zipcode,address_street,address_number,address_city_id,address_district,address_complement) VALUES ('Soho',30,2,'40015-160','Rua Lafayete Coutinho','1010',2,'Bahia Marina','');

insert into payment_method (id, description) values (1, 'Credit Card');
insert into payment_method (id, description) values (2, 'Debit Card');
insert into payment_method (id, description) values (3, 'Money');

insert into permission (id, name, description) values (1, 'CONSULT', 'Allows you consult cuisines');
insert into permission (id, name, description) values (2, 'EDIT', 'Allows you to edit cuisines');
insert into restaurant_payment_method (restaurant_id, payment_method_id) values (1,1), (1,2), (1,3), (2,3);
