SELECT id, name, email,
      phone_number, country, region,
      city, street_address, specialization,
      is_deleted
FROM manufacturers WHERE email = ? AND is_deleted = false