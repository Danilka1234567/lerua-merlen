CREATE TABLE IF NOT EXISTS products(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    warehouse_id BIGINT NOT NULL,
    manufacturer_id BIGINT NOT NULL,
    price NUMERIC(12, 2) CHECK(price > 0),
    discount NUMERIC(3, 2) CHECK(discount >= 0 AND discount < 1),
    is_deleted BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_products_warehouse
        FOREIGN KEY (warehouse_id)
        REFERENCES warehouses(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_products_manufacturer
        FOREIGN KEY (manufacturer_id)
        REFERENCES manufacturers(id)
        ON DELETE RESTRICT
)