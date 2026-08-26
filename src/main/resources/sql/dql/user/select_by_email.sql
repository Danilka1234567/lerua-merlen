SELECT id, name, email, phone_number, password, role, is_deleted FROM users
WHERE email = ? AND is_deleted = false