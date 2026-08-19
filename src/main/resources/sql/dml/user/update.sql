UPDATE users SET
    name = ?,
    email = ?,
    phone_number = ?,
    password = ?,
    role = ?
WHERE id = ? AND is_deleted = false