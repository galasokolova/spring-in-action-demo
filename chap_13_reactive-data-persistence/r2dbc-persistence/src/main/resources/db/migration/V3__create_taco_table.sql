-- V3__create_taco_table.sql
CREATE TABLE IF NOT EXISTS taco (
                                    id BIGSERIAL PRIMARY KEY,
                                    name VARCHAR(50) NOT NULL,
                                    ingredient_ids BIGINT[]  -- Массив для хранения ID ингредиентов
);
