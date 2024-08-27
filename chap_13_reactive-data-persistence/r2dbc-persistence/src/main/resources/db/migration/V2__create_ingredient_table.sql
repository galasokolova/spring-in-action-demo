-- V2__create_ingredient_table.sql
CREATE TABLE IF NOT EXISTS ingredient (
                                          id BIGSERIAL PRIMARY KEY,
                                          slug VARCHAR(4) NOT NULL,
                                          name VARCHAR(25) NOT NULL,
                                          type VARCHAR(10) NOT NULL
);
