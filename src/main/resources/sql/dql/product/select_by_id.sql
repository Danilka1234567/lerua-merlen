SELECT
    p.id AS product_id,
    p.name AS product_name,
    p.price,
    p.discount,
    p.registration_date AS product_registration_date,
    p.is_deleted AS product_is_deleted,
    m.id AS manufacturer_id,
    m.name AS manufacturer_name,
    m.email AS manufacturer_email,
    m.phone_number AS manufacturer_phone_number,
    m.country AS manufacturer_country,
    m.region AS manufacturer_region,
    m.city AS manufacturer_city,
    m.street_address AS manufacturer_street_address,
    m.specialization,
    m.registration_date AS manufacturer_registration_date,
    m.is_deleted AS manufacturer_is_deleted,
    w.id AS warehouse_id,
    w.phone_number AS warehouse_phone_number,
    w.email AS warehouse_email,
    w.country AS warehouse_country,
    w.region AS warehouse_region,
    w.city AS warehouse_city,
    w.street_address AS warehouse_street_address,
    w.capacity,
    w.registration_date AS warehouse_registration_date,
    w.is_deleted AS warehouse_is_deleted
FROM products p
JOIN manufacturers m ON p.manufacturer_id = m.id
JOIN warehouses w ON p.warehouse_id = w.id
WHERE p.id = ? AND p.is_deleted = false