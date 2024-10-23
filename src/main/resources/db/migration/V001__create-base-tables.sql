create table tb_state (
    id bigint not null auto_increment,
    name varchar(40) not null,
    primary  key(id)
) engine=InnoDB default charset=utf8;

create table tb_city (
    id bigint not null auto_increment,
    name varchar(40) not null,
    state_id bigint not null,
    primary key(id),
    constraint fk_city_state foreign key(state_id) references tb_state(id)
) engine=InnoDB default charset=utf8;

create table tb_group (
    id bigint not null auto_increment,
    name varchar(40) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_permission (
    id bigint not null auto_increment,
    name varchar(40) not null,
    description varchar(255) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_group_permission (
    group_id bigint not null,
    permission_id bigint not null,
    constraint fk_group_permission_group foreign key (group_id) references tb_group(id),
    constraint fk_group_permission_permission foreign key (permission_id) references tb_permission(id)
) engine=InnoDB default charset=utf8;

create table tb_kitchen (
    id bigint not null auto_increment,
    name varchar(40) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_payment_method (
    id bigint not null auto_increment,
    description varchar(255) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_restaurant (
    id bigint not null auto_increment,
    name varchar(40),
    kitchen_id bigint not null,
    is_active boolean default false,
    is_open boolean default false,
    address_city_id bigint not null,
    address_district varchar(40) not null,
    address_number varchar(10) not null,
    address_postal_code varchar(30) not null,
    address_complement varchar(150) not null,
    fee decimal(10,2) not null default 0.00,
    registration_date timestamp not null,
    update_date timestamp not null,
    primary key (id),
    constraint fk_restaurant_kitchen foreign key (kitchen_id) references tb_kitchen(id),
    constraint fk_restaurant_city foreign key (address_city_id) references tb_city(id)
) engine=InnoDB default charset=utf8;

create table tb_product (
    id bigint not null auto_increment,
    name varchar(40) not null,
    description varchar(255) not null,
    active boolean default false,
    restaurant_id bigint,
    value decimal(10,2) not null,
    primary key (id),
    constraint fk_product_restaurant foreign key (restaurant_id) references tb_restaurant(id)
) engine=InnoDB default charset=utf8;

create table tb_restaurant_payment_method (
    restaurant_id bigint not null,
    payment_method_id bigint not null,
    constraint fk_restaurant_payment_restaurant foreign key (restaurant_id) references tb_restaurant(id),
    constraint fk_restaurant_payment_payment_method foreign key fk(payment_method_id) references tb_payment_method(id)
) engine=InnoDB default charset=utf8;

create table tb_user (
    id bigint not null auto_increment,
    name varchar(40) not null,
    email varchar(40) not null,
    password varchar(150) not null,
    registration_date timestamp not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table tb_restaurant_responsible (
    restaurant_id bigint not null,
    responsible_id bigint not null,
    constraint fk_restaurant_responsible_restaurant foreign key (restaurant_id) references tb_restaurant(id),
    constraint fk_restaurant_responsible_responsible foreign key (responsible_id) references tb_user(id)
) engine=innoDB default charset=utf8;

create table tb_user_group (
    user_id bigint not null,
    group_id bigint not null,
    constraint fk_user_group_user foreign key (user_id) references tb_user(id),
    constraint fk_user_group_group foreign key (group_id) references tb_group(id)
) engine=InnoDB default charset=utf8;