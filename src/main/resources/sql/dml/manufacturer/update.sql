UPDATE manufacturers SET
    name = ?, email = ?, phone_number = ?,
    country = ?, region = ?, city = ?,
    street_address = ?, specialization = ?
WHERE id = ? AND is_deleted = false