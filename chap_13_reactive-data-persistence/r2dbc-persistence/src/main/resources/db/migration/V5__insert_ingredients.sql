-- Удаление существующих данных
DELETE FROM taco;
DELETE FROM taco_order;
DELETE FROM ingredient;

-- Вставка новых ингредиентов
INSERT INTO ingredient (slug, name, type) VALUES ('FLTO', 'Flour Tortilla', 'WRAP');
INSERT INTO ingredient (slug, name, type) VALUES ('COTO', 'Corn Tortilla', 'WRAP');
INSERT INTO ingredient (slug, name, type) VALUES ('GRBF', 'Ground Beef', 'PROTEIN');
INSERT INTO ingredient (slug, name, type) VALUES ('CARN', 'Carnitas', 'PROTEIN');
INSERT INTO ingredient (slug, name, type) VALUES ('TMTO', 'Diced Tomatoes', 'VEGGIES');
INSERT INTO ingredient (slug, name, type) VALUES ('LETC', 'Lettuce', 'VEGGIES');
INSERT INTO ingredient (slug, name, type) VALUES ('CHED', 'Cheddar', 'CHEESE');
INSERT INTO ingredient (slug, name, type) VALUES ('JACK', 'Monterrey Jack', 'CHEESE');
INSERT INTO ingredient (slug, name, type) VALUES ('SLSA', 'Salsa', 'SAUCE');
INSERT INTO ingredient (slug, name, type) VALUES ('SRCR', 'Sour Cream', 'SAUCE');
