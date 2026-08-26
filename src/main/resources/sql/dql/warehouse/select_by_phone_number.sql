SELECT id, email, phone_number, country, region, city, street_address, capacity, is_deleted
WHERE phone_number = ? AND is_deleted = false