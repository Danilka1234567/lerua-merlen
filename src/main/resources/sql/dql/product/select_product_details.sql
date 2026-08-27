SELECT
    p.name AS product_name,
    p.price,
    m.name AS manufacturer_name,
    w.city,
    u.name AS last_customer_name,
    s.expiration_date
FROM products p
JOIN manufacturers m ON p.manufacturer_id = m.id
JOIN warehouses w ON p.warehouse_id = w.id
LEFT JOIN orders o ON p.id = o.product_id AND o.is_deleted = FALSE
LEFT JOIN users u ON o.user_id = u.id AND u.is_deleted = FALSE
LEFT JOIN sessions s ON u.id = s.user_id AND s.is_deleted = FALSE
WHERE p.is_deleted = FALSE
  AND m.is_deleted = FALSE
  AND w.is_deleted = FALSE
  AND m.name LIKE ?