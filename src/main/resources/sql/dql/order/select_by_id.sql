SELECT
    o.id AS order_id,
    o.country,
    o.region,
    o.city,
    o.street_address,
    o.delivery_period,
    o.is_deleted AS order_is_deleted,
    u.id AS user_id,
    u.name AS user_name,
    u.email,
    u.phone_number,
    u.role,
    u.is_deleted AS user_is_deleted,
    p.id AS product_id,
    p.name AS product_name,
    p.warehouse_id,
    p.manufacturer_id,
    p.price,
    p.discount,
    p.is_deleted AS product_is_deleted
FROM orders o
JOIN users u ON o.user_id = u.id
JOIN products p ON o.product_id = p.id
WHERE o.id = ? AND o.is_deleted = false