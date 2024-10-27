insert into tb_state (name) values ("Goiás");
insert into tb_state (name) values ("São Paulo");
insert into tb_state (name) values ("Rio de Janeiro");
insert into tb_state (name) values ("Maranhão");
insert into tb_state (name) values ("Distrito Federal");
insert into tb_state (name) values ("Paraná");

insert into tb_city (name, state_id) values ("Aparecida", 1);
insert into tb_city (name, state_id) values ("Anápolis", 1);
insert into tb_city (name, state_id) values ("Formosa", 1);
insert into tb_city (name, state_id) values ("Trindade", 1);
insert into tb_city (name, state_id) values ("Adamantina", 2);
insert into tb_city (name, state_id) values ("São Gonçalo", 3);
insert into tb_city (name, state_id) values ("Porto Franco", 4);
insert into tb_city (name, state_id) values ("Taguatinga", 5);
insert into tb_city (name, state_id) values ("Cascavel", 6);

insert into tb_kitchen (name) values ("Brasileira");
insert into tb_kitchen (name) values ("Itáliana");
insert into tb_kitchen (name) values ("Japonesa");
insert into tb_kitchen (name) values ("Mexicana");
insert into tb_kitchen (name) values ("Turca");
insert into tb_kitchen (name) values ("Espanhola");

insert into tb_user(id, name, email, password, registration_date) values (1, "João Santos", "JoãoSantos@email.com", "$2a$12$VdtSHeqF1aMjP8YGVXDkAeN1M5c4rq7InkY6bB17yjTc.SniqNDvO", utc_timestamp);
insert into tb_user(id, name, email, password, registration_date) values (2, "Maria Silva", "MariaSilva@email.com", "$2a$12$VdtSHeqF1aMjP8YGVXDkAeN1M5c4rq7InkY6bB17yjTc.SniqNDvO", utc_timestamp);
insert into tb_user(id, name, email, password, registration_date) values (3, "Ana Martins", "AnaMartins@email.com", "$2a$12$VdtSHeqF1aMjP8YGVXDkAeN1M5c4rq7InkY6bB17yjTc.SniqNDvO", utc_timestamp);
insert into tb_user(id, name, email, password, registration_date) values (4, "José Mattos", "JoséMattos@email.com", "$2a$12$VdtSHeqF1aMjP8YGVXDkAeN1M5c4rq7InkY6bB17yjTc.SniqNDvO", utc_timestamp);
insert into tb_user(id, name, email, password, registration_date) values (5, "Carla Rodrigues", "CarlaRodrigues@email.com", "$2a$12$VdtSHeqF1aMjP8YGVXDkAeN1M5c4rq7InkY6bB17yjTc.SniqNDvO", utc_timestamp);
insert into tb_user(id, name, email, password, registration_date) values (6, "Thais Sousa", "ThaisSousa@email.com", "$2a$12$VdtSHeqF1aMjP8YGVXDkAeN1M5c4rq7InkY6bB17yjTc.SniqNDvO", utc_timestamp);

insert into tb_permission(id, name, description) values (1, "CONSULT_KITCHEN","allows to consult kitchens");
insert into tb_permission(id, name, description) values (2, "EDIT_KITCHEN","allows to create and edit kitchens");
insert into tb_permission(id, name, description) values (3, "CONSULT_PAYMENT_METHOD","allows to consult payment methods");
insert into tb_permission(id, name, description) values (4, "EDIT_PAYMENT_METHOD","allows to create and edit payment methods");
insert into tb_permission(id, name, description) values (5, "CONSULT_CITY)","allows to consult cities");
insert into tb_permission(id, name, description) values (6, "EDIT_CITY","allows to create and edit cities");
insert into tb_permission(id, name, description) values (7, "CONSULT_STATE","allows to consult states");
insert into tb_permission(id, name, description) values (8, "EDIT_STATE","allows to create and consult states");
insert into tb_permission(id, name, description) values (9, "CONSULT_USER","allows to consult users");
insert into tb_permission(id, name, description) values (10, "EDIT_USER","allows to create and consult users");
insert into tb_permission(id, name, description) values (11, "CONSULT_RESTAURANT","allows to consult restaurants");
insert into tb_permission(id, name, description) values (12, "EDIT_RESTAURANT","allows to create and consult restaurants");
insert into tb_permission(id, name, description) values (13, "CONSULT_PRODUCT","allows to consult products");
insert into tb_permission(id, name, description) values (14, "EDIT_PRODUCT","allows to create and consult products");
insert into tb_permission(id, name, description) values (15, "CONSULT_ORDER","allows to consult orders");
insert into tb_permission(id, name, description) values (16, "MANAGE_ORDER","allows to manage orders");
insert into tb_permission(id, name, description) values (17, "CREATE_REPORT","allows to create reports");

insert into tb_group(id, name) values (1, "manager");
insert into tb_group(id, name) values (2, "seller");
insert into tb_group(id, name) values (3, "assistant");
insert into tb_group(id, name) values (4, "registrar");

insert into tb_group_permission(group_id, permission_id)
select 1, id from tb_permission;

insert into tb_group_permission(group_id, permission_id)
select 2, id from tb_permission where name like "CONSULT_%";

insert into tb_group_permission(group_id, permission_id) values (2, 14);

insert into tb_group_permission(group_id, permission_id)
select 3, id from tb_permission where name like "CONSULT_%";

insert into tb_group_permission(group_id, permission_id)
select 4, id from tb_permission where name like "%_RESTAURANT" or name like "%_PRODUCT";

insert into tb_payment_method(description) values ("pix");
insert into tb_payment_method(description) values ("debit card");
insert into tb_payment_method(description) values ("credit card");

insert into tb_restaurant(code, name, kitchen_id, is_active, is_open, address_city_id, address_district, address_number, address_postal_code, address_complement, fee, registration_date, update_date)
values ("11b899b4-669d-42db-9c0e-b4d2763f6702", "Madeiro", 1, true, true, 2, "Centro", 12, 65052410, "próximo a algum lugar", 20.00, utc_timestamp, utc_timestamp);

insert into tb_restaurant(code, name, kitchen_id, is_active, is_open, address_city_id, address_district, address_number, address_postal_code, address_complement, fee, registration_date, update_date)
values ("47c88854-4800-4ac6-8a31-171776903143", "Pobre Juan", 2, true, true, 4, "Zona Sul", 45, 70012308, "próximo a algum lugar", 15.00, utc_timestamp, utc_timestamp);

insert into tb_restaurant(code, name, kitchen_id, is_active, is_open, address_city_id, address_district, address_number, address_postal_code, address_complement, fee, registration_date, update_date)
values ("e03c66a1-bb95-45ff-ba7c-14ccc649e62b", "Tradição Mexícana", 4, true, true, 3, "Zona Norte", 13, 43329899, "próximo a algum lugar", 32.00, utc_timestamp, utc_timestamp);

insert into tb_restaurant(code, name, kitchen_id, is_active, is_open, address_city_id, address_district, address_number, address_postal_code, address_complement, fee, registration_date, update_date)
values ("88b4b4a2-5706-4ee0-a542-039515c37d3a", "Tradição Japonesa", 3, true, true, 1, "Centro", 85, 15342455, "próximo a algum lugar", 16.00, utc_timestamp, utc_timestamp);

insert into tb_restaurant(code, name, kitchen_id, is_active, is_open, address_city_id, address_district, address_number, address_postal_code, address_complement, fee, registration_date, update_date)
values ("6f9cfd9e-d108-4639-838b-2200f9f05933", "Tradição Turca", 5, true, true, 6, "Centro", 55, 3554665, "próximo a algum lugar", 00.00, utc_timestamp, utc_timestamp);

insert into tb_restaurant(code, name, kitchen_id, is_active, is_open, address_city_id, address_district, address_number, address_postal_code, address_complement, fee, registration_date, update_date)
values ("4d8434fd-1837-40ee-a3b1-a296d0d57582", "Tradição Espanhola", 6, true, true, 5, "Centro", 15, 6622241, "próximo a algum lugar", 00.00, utc_timestamp, utc_timestamp);

insert into tb_product(name, description, active, restaurant_id, value) values ("Arroz de Forno", "Feito com arroz cozido, queijo, presunto, tomate, ervilha e milho, é misturado e levado ao forno para gratinar. ", true, 5, 45.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Bolo de Carne Vegano", "Feito com proteína de soja, farinha de trigo, cebola, alho e óleo, é assado no forno até ficar dourado e crocante por fora. ", true, 1, 25.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Costelinha de Porco", "Feita com costelinha de porco, cebola, alho e molho de tomate, é cozida na panela de pressão até ficar macia e suculenta. Ideal para ser servida com arroz e feijão, batatas ou legumes grelhados.", true, 2, 50.50);
insert into tb_product(name, description, active, restaurant_id, value) values ("Filé de frango com molho de mostarda", " Feito com filé de frango, mostarda, creme de leite e cebola, é grelhado na frigideira até ficar dourado e suculento. Ideal para ser servido com arroz, salada ou legumes.", true, 3, 29.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Frango Xadrez", "Ele vem da culinária chinesa e é feito com filé de frango, pimentões, cebola, amendoim e molho shoyu, é salteado na wok.", true, 4, 36.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Tutu de Feijão", "É feito com feijão cozido e triturado, misturado com farinha de mandioca e temperos como cebola, alho e bacon. ", true, 5, 42.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Bolinho de Brócolis Assado", "Feito com brócolis, queijo parmesão, alho e ovo, é assado no forno até ficar dourado e crocante por fora. Ideal para ser servido como petisco ou acompanhamento.", true, 6, 28.45);
insert into tb_product(name, description, active, restaurant_id, value) values ("Estrogonofe", "Pode ser feito de camarão, frango ou carne bovina, normalmente é servido com arroz branco e batata palha. Uma opção popular em restaurantes e muito fácil de preparar em casa.", true, 1, 60.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Lasanha de abobrinha", "Feita com camadas de abobrinha grelhada, molho de tomate e queijo derretido, é uma opção vegetariana e muito saborosa. Pode ser servida como prato principal ou acompanhamento.", true, 2, 69.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Salada de Frutos do Mar", "Com camarão, lula e polvo, este prato oferece uma explosão de sabores do oceano.", true, 3, 70.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Bife ao Molho de Vinho", " O bife é grelhado e é combinado com molho de vinho tinto encorpado adicionando sabores irresistíveis.", true, 4, 66.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Macarrão à Carbonara", "O molho feito com creme de leite, queijo parmesão e bacon crocante, juntamente aos ovos e a pimenta, proporcionam uma experiência especial.", true, 5, 55.20);
insert into tb_product(name, description, active, restaurant_id, value) values ("Risoto de Cogumelos", "O Risoto de Cogumelos é um prato sofisticado e saboroso. Feito com cogumelos, e enriquecido com vinho branco e caldo de legumes, oferece uma textura cremosa.", true, 6, 36.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Stake Frites", "um prato simples com um toque de sofisticação. Consiste apenas em um bife acompanhado de batatas fritas crocantes.", true, 1, 28.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Salmão Grelhado", "O salmão é servido com legumes frescos e uma salada verde, criando uma refeição equilibrada e saborosa.", true, 2, 75.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Coq au Vin", "Esse prato tradicional é um guisado de frango cozido em vinho tinto, com bacon, cogumelos e ervas aromáticas.", true, 3, 59.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Cassoulet", "Este combina feijão branco com carne de porco, linguiça e confit de pato, criando uma refeição cheia de sabor. ", true, 4, 72.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Tarte Tatin", "Esta torta de maçãs caramelizadas é envolta em uma massa folhada crocante, oferecendo uma combinação perfeita de doçura e textura.", true, 5, 64.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Salada de feijão fradinho simples", " Feita com feijão fradinho cozido, tomate, cebola e coentro, é uma opção saudável e rica em proteínas.", true, 6, 25.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Salada de brócolis com figo", "Feita com brócolis cozido al dente, figos frescos fatiados, queijo feta esfarelado e nozes picadas, é uma opção saudável e deliciosa para qualquer refeição.", true, 1, 22.00);
insert into tb_product(name, description, active, restaurant_id, value) values ("Escondidinho de carne moída", "Feito com uma camada de purê de mandioca, uma camada de carne moída refogada e coberto com queijo derretido, é uma opção reconfortante e deliciosa para qualquer ocasião.", true, 2, 33.00);

insert into tb_user_group(user_id, group_id) values (1, 1);
insert into tb_user_group(user_id, group_id) values (1, 2);
insert into tb_user_group(user_id, group_id) values (2, 3);
insert into tb_user_group(user_id, group_id) values (3, 2);
insert into tb_user_group(user_id, group_id) values (4, 3);
insert into tb_user_group(user_id, group_id) values (5, 3);

insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (1, 5);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (1, 4);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (2, 3);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (3, 2);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (4, 1);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (5, 4);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (5, 1);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (6, 1);
insert into tb_restaurant_responsible(restaurant_id, responsible_id) values (6, 2);

insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (1, 1);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (1, 2);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (1, 3);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (2, 1);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (2, 3);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (3, 1);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (3, 2);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (4, 1);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (4, 2);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (4, 3);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (5, 1);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (5, 2);
insert into tb_restaurant_payment_method(restaurant_id, payment_method_id) values (6, 3);

insert into tb_order(client_user_id, restaurant_id, payment_method_id, order_status, address_city_id, address_postal_code, address_number, address_complement, address_district, subtotal, delivery_fee, total_value, creation_date, confirmation_date, cancellation_date, delivery_date)
values (1, 1, 2, "CREATED", 1, "875462", "33", "proxímo ao supermerdado lider", "centro", 140.00, 20.00, 160.00, utc_timestamp, null, null, null);

insert into tb_order_item(product_id, order_id, quantity, unit_value, total_value, observation)
values (2, 1, 2, 25.00, 50.00, "saindo para a entrega");

insert into tb_order_item(product_id, order_id, quantity, unit_value, total_value, observation)
values (1, 1, 2, 40.00, 90.00, "saindo para entrega");