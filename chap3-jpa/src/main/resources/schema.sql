create table if not exists Taco_Order (
                                          id identity,
                                          delivery_Name varchar(50) not null,
                                          delivery_Street varchar(50) not null,
                                          delivery_City varchar(50) not null,
                                          delivery_State varchar(20) not null,
                                          delivery_Zip varchar(10) not null,
                                          cc_number varchar(16) not null,
                                          cc_expiration varchar(5) not null,
                                          cc_cvv varchar(3) not null,
                                          placed_at timestamp not null
);

create table if not exists Taco (
                                    id identity,
                                    name varchar(50) not null,
                                    taco_order_id bigint not null,
                                    created_at timestamp not null
);

create table if not exists Ingredient (
                                          id varchar(4) not null,
                                          name varchar(25) not null,
                                          type varchar(10) not null,
                                          PRIMARY KEY (id)
);

create table if not exists Ingredient_Ref (
                                              ingredient varchar(4) not null,
                                              taco bigint not null
);

create table if not exists taco_ingredients (
                                                taco_id bigint not null,
                                                ingredients_id varchar(4) not null,
                                                primary key (taco_id, ingredients_id),
                                                foreign key (taco_id) references Taco(id),
                                                foreign key (ingredients_id) references Ingredient(id)
);

alter table Taco
    add foreign key (taco_order_id) references Taco_Order(id);

alter table Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);
