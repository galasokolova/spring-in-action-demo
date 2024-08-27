-- V1__create_app_user_table.sql

CREATE TABLE IF NOT EXISTS app_user (
                                        id BIGSERIAL PRIMARY KEY,
                                        username VARCHAR(50) NOT NULL UNIQUE,
                                        password VARCHAR(100) NOT NULL,
                                        fullname VARCHAR(100) NOT NULL,
                                        street VARCHAR(100),
                                        city VARCHAR(50),
                                        state VARCHAR(50),
                                        zip VARCHAR(10),
                                        phone VARCHAR(15)
);
