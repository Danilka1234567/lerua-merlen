SELECT
    s.id AS session_id,
    s.expiration_date,
    s.is_deleted AS session_is_deleted,
    u.id AS user_id,
    u.name,
    u.email,
    u.phone_number,
    u.password,
    u.role,
    u.registration_date,
    u.is_deleted AS user_is_deleted
FROM sessions s
JOIN users u ON s.user_id = u.id
WHERE u.id = ? AND s.is_deleted = false