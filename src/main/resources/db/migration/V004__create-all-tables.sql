create table family (
  id bigint not null auto_increment,
  name varchar(60) not null,
  primary key (id)
) engine = InnoDB default charset=utf8;
create table family_permission (
  family_id bigint not null, permission_id bigint not null
) engine = InnoDB default charset=utf8;
create table payment_method (
  id bigint not null auto_increment,
  description varchar(60) not null,
  primary key (id)
) engine = InnoDB default charset=utf8;
create table permission (
  id bigint not null auto_increment,
  description varchar(150) not null,
  name varchar(60) not null,
  primary key (id)
) engine = InnoDB default charset=utf8;
create table product (
  id bigint not null auto_increment,
  active tinyint(1) not null,
  description text not null,
  name varchar(60) not null,
  price decimal(15, 2) not null,
  restaurant_id bigint not null,
  primary key (id)
) engine = InnoDB default charset=utf8;
create table restaurant (
  id bigint not null auto_increment,
  freight_rate decimal(15, 2) not null,
  name varchar(60) not null,
  registration_date datetime not null,
  update_date datetime not null,

  address_complement varchar(60),
  address_district varchar(60),
  address_number varchar(10),
  address_street varchar(150),
  address_zipcode varchar(9),
  address_city_id bigint,
  cuisine_id bigint not null,
  primary key (id)
) engine = InnoDB default charset=utf8;
create table restaurant_payment_method (
  restaurant_id bigint not null, payment_method_id bigint not null,
  primary key (restaurant_id, payment_method_id)
) engine = InnoDB default charset=utf8;
create table user (
  id bigint not null auto_increment,
  email varchar(255) not null,
  name varchar(100) not null,
  registration_date datetime not null,
  primary key (id)
) engine = InnoDB default charset=utf8;
create table user_group (
  user_id bigint not null, group_id bigint not null,
  primary key (user_id, group_id)
) engine = InnoDB default charset=utf8;
alter table
  family_permission
add
  constraint fk_family_permission_permission foreign key (permission_id) references permission (id);
alter table
  family_permission
add
  constraint fk_family_permission_family foreign key (family_id) references family (id);
alter table
  product
add
  constraint fk_product_restaurant foreign key (restaurant_id) references restaurant (id);
alter table
  restaurant
add
  constraint fk_restaurant_city foreign key (address_city_id) references city (id);
alter table
  restaurant
add
  constraint fk_restaurant_cuisine foreign key (cuisine_id) references cuisine (id);
alter table
  restaurant_payment_method
add
  constraint fk_rest_payment_method_payment foreign key (payment_method_id) references payment_method (id);
alter table
  restaurant_payment_method
add
  constraint fk_rest_payment_method_restaurant foreign key (restaurant_id) references restaurant (id);
alter table
  user_group
add
  constraint fk_user_group_family foreign key (group_id) references family (id);
alter table
  user_group
add
  constraint fk_user_group_user foreign key (user_id) references user (id);