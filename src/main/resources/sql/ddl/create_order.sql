CREATE TABLE IF NOT EXISTS orders(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    product_id BIGINT,
    country VARCHAR(64) NOT NULL,
    region VARCHAR(128) NOT NULL,
    city VARCHAR(64) NOT NULL,
    street_address VARCHAR(128) NOT NULL,
    registration_date DATE DEFAULT CURRENT_DATE,
    delivery_period INT NOT NULL CHECK(delivery_period > 0),
    is_deleted BOOLEAN DEFAULT FALSE,

    CONSTRAINT unique_product_order UNIQUE(user_id, product_id),

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_orders_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE SET NULL
)