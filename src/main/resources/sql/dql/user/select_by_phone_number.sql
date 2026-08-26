SELECT id, name, email, phone_number, password, role, is_deleted FROM users
WHERE phone_number = ? AND is_deleted = false