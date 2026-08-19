SELECT id, name, email,
      phone_number, country, region,
      city, street_address, specialization,
      registration_date, is_deleted
FROM manufacturers WHERE country = ? AND region = ? AND city = ? AND street_address = ? AND is_deleted = false