SELECT id, email, phone_number, country, region, city, street_address, capacity, registration_date, is_deleted
WHERE id = ? AND is_deleted = false