SELECT id, email, phone_number, country, region, city, street_address, capacity, is_deleted
WHERE email = ? AND is_deleted = false