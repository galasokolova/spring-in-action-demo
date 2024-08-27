-- V4__create_taco_order_table.sql
CREATE TABLE IF NOT EXISTS taco_order (
                                          id BIGSERIAL PRIMARY KEY,
                                          delivery_name VARCHAR(50) NOT NULL,
                                          delivery_street VARCHAR(50) NOT NULL,
                                          delivery_city VARCHAR(50) NOT NULL,
                                          delivery_state VARCHAR(20) NOT NULL,
                                          delivery_zip VARCHAR(10) NOT NULL,
                                          cc_number VARCHAR(16) NOT NULL,
                                          cc_expiration VARCHAR(5) NOT NULL,
                                          cc_cvv VARCHAR(3) NOT NULL,
                                          taco_ids BIGINT[] DEFAULT '{}',  -- Массив для хранения ID такосов
                                          user_id BIGINT,
                                          placed_at TIMESTAMP,
                                          CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);
