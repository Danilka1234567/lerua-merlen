SELECT id, email, phone_number, country, region, city, street_address, capacity, is_deleted FROM warehouses
WHERE registration_date = ? AND is_deleted = false