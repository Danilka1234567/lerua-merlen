SELECT id, email, phone_number, country, region, city, street_address, capacity, is_deleted FROM warehouses
WHERE country = ? AND region = ? AND city = ? AND street_address = ? AND is_deleted = false