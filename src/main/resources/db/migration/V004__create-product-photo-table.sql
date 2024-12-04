create table tb_product_photo (
id bigint not null auto_increment,
file_name varchar(40) not null,
description varchar(255) not null,
content_type varchar(10) not null,
product_id bigint not null,
size bigint not null,
primary key (id),
constraint fk_product_photo_product foreign key (product_id) references tb_product(id)
) engine=InnoDB default charset=utf8;
