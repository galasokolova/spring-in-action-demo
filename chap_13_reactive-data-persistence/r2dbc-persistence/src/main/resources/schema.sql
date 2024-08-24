create table if not exists AppUser (
                                       id bigint auto_increment primary key,
                                       username varchar(50) not null unique,
                                       password varchar(100) not null,
                                       fullname varchar(100) not null,
                                       street varchar(100),
                                       city varchar(50),
                                       state varchar(50),
                                       zip varchar(10),
                                       phone varchar(15)
);



create table if not exists Ingredient (
                                          id identity,
                                          slug varchar(4) not null,
                                          name varchar(25) not null,
                                          type varchar(10) not null
);

create table if not exists Taco (
                                    id identity,
                                    name varchar(50) not null,
                                    ingredient_ids BIGINT ARRAY
);


create table if not exists Taco_Order (
                                          id bigint auto_increment primary key,
                                          delivery_name varchar(50) not null,
                                          delivery_street varchar(50) not null,
                                          delivery_city varchar(50) not null,
                                          delivery_state varchar(20) not null,
                                          delivery_zip varchar(10) not null,
                                          cc_number varchar(16) not null,
                                          cc_expiration varchar(5) not null,
                                          cc_cvv varchar(3) not null,
                                          taco_ids INT ARRAY,
                                          user_id bigint,  -- Connecting orders with the table AppUser
                                          placed_at TIMESTAMP,
                                          constraint fk_user foreign key (user_id) references AppUser(id) on delete cascade
);

