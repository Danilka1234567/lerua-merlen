SELECT id, email, phone_number, country, region, city, street_address, capacity, is_deleted
WHERE city = ? AND is_deleted = false