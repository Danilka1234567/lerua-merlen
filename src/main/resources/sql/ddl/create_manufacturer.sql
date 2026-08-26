CREATE TABLE IF NOT EXISTS manufacturers(

    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(11) UNIQUE,
    country VARCHAR(64) NOT NULL,
    region VARCHAR(128) NOT NULL,
    city VARCHAR(64) NOT NULL,
    street_address VARCHAR(128) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,

    CONSTRAINT manufacturers_unique_address UNIQUE (country, region, city, street_address)
)
