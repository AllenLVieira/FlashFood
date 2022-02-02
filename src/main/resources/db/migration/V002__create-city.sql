create table city(
	id bigint not null auto_increment,
    city_name varchar(60) not null,
    state_name varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;