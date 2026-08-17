SELECT id, name, email, phone_number, password, role, registration_date, is_deleted FROM users
WHERE id = ? AND is_deleted = false