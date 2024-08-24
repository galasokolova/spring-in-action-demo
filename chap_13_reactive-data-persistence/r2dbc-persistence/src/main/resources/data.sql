delete from Taco;
delete from Taco_Order;
delete from Ingredient;

insert into Ingredient (slug, name, type)
values ('FLTO', 'Flour Tortilla', 'WRAP');

insert into Ingredient (slug, name, type)
values ('COTO', 'Corn Tortilla', 'WRAP');

insert into Ingredient (slug, name, type)
values ('GRBF', 'Ground Beef', 'PROTEIN');

insert into Ingredient (slug, name, type)
values ('CARN', 'Carnitas', 'PROTEIN');

insert into Ingredient (slug, name, type)
values ('TMTO', 'Diced Tomatoes', 'VEGGIES');

insert into Ingredient (slug, name, type)
values ('LETC', 'Lettuce', 'VEGGIES');

insert into Ingredient (slug, name, type)
values ('CHED', 'Cheddar', 'CHEESE');

insert into Ingredient (slug, name, type)
values ('JACK', 'Monterrey Jack', 'CHEESE');

insert into Ingredient (slug, name, type)
values ('SLSA', 'Salsa', 'SAUCE');

insert into Ingredient (slug, name, type)
values ('SRCR', 'Sour Cream', 'SAUCE');
