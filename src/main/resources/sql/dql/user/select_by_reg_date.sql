SELECT id, name, email, phone_number, password, role, registration_date, is_deleted FROM users
WHERE registration_date = ? AND is_deleted = false