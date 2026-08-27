SELECT id, email, phone_number, country, region, city, street_address, capacity, is_deleted FROM warehouses
WHERE id = ? AND is_deleted = false