SELECT id, email, phone_number, country, region, city, street_address, capacity, registration_date, is_deleted
WHERE email = ? AND is_deleted = false