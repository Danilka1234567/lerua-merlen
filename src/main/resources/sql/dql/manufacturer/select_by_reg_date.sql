SELECT id, name, email,
      phone_number, country, region,
      city, street_address, specialization,
      registration_date, is_deleted
FROM manufacturers WHERE registration_date = ? AND is_deleted = false