SELECT id, email, phone_number, country, region, city, street_address, capacity, registration_date, is_deleted
WHERE registration_date >= ? AND registration_date <= ? AND is_deleted = false