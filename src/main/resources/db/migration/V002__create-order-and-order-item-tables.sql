create table tb_order (
    id bigint not null auto_increment,
    client_user_id bigint not null,
    restaurant_id bigint not null,
    payment_method_id bigint not null,
    order_status varchar(10) not null,
    address_city_id bigint not null,
    address_postal_code varchar(30) not null,
    address_number varchar(10) not null,
    address_complement varchar(150) not null,
    address_district varchar(40) not null,
    subtotal decimal(10,2) not null,
    delivery_fee decimal(10,2) not null,
    total_value decimal(10,2) not null,
    creation_date timestamp,
    confirmation_date timestamp,
    cancellation_date timestamp,
    delivery_date timestamp,
    primary key (id),
    constraint fk_order_user foreign key (client_user_id) references tb_city(id),
    constraint fk_order_restaurant foreign key (restaurant_id) references tb_restaurant(id),
    constraint fk_order_payment_method foreign key (payment_method_id) references tb_payment_method(id),
    constraint fk_order_city foreign key (address_city_id) references tb_city(id)
) engine=InnoDB default charset=utf8;

create table tb_order_item (
    id bigint not null auto_increment,
    product_id bigint not null,
    order_id bigint not null,
    quantity smallint not null,
    unit_value decimal(10,2) not null,
    total_value decimal(10,2) not null,
    observation varchar(255),
    primary key (id),
    constraint fk_order_item_product foreign key (product_id) references tb_product(id),
    constraint fk_order_item_order foreign key (order_id) references tb_order(id)
) engine=InnoDB default charset=utf8;