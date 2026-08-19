UPDATE orders SET
    user_id = ?,
    product_id = ?,
    country = ?,
    region = ?,
    city = ?,
    street_address = ?,
    delivery_period = ?
WHERE id = ? AND is_deleted = false