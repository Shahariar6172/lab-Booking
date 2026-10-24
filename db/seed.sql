-- ডেমো ডাটা। চালানোর নিয়ম:  mysql -u root < db/seed.sql
-- schema.sql আগে চালাতে হবে (ওটা টেবিল মুছে নতুন বানায়)।

USE lab_booking;

-- তিনটা অ্যাকাউন্টের পাসওয়ার্ড একই: password123
-- হ্যাশ ফরম্যাট: saltHex$sha256Hex(saltHex + password) — Phase 3 এর কোড এভাবেই মেলাবে।
INSERT INTO users (full_name, email, password_hash, role) VALUES
  ('Lab Administrator', 'admin@lab.edu',  'a1b2c3d4e5f60718$b90f4bebd6a802058aa94ae1342fa683358e42caa59b78f1c1fbbe854ff18c07', 'ADMIN'),
  ('Al-Shahariar ',   'shahariar@lab.edu',  '0f1e2d3c4b5a6978$431e12094b57b10756044f6b51a2a72fd32e9e4aef59ca3a5739c24d200fe271', 'STUDENT'),
  ('Nusrat Jahan',      'nusrat@lab.edu', '1122334455667788$007b6e73fb8356defe2704fb3dff1766f82838c89594d7e7d215bd068fe49cdd', 'STUDENT');

-- দুইটা সাবজেক্টে ছয়টা ক্যাটালগ এন্ট্রি।
INSERT INTO materials (subject, name, variant) VALUES
  ('Digital Logic Design', 'Breadboard',   '830 points'),
  ('Digital Logic Design', 'Logic IC',     '7400 NAND'),
  ('Digital Logic Design', 'Jumper Wires', 'Male-Male'),
  ('Microprocessor Lab',   'Arduino Uno',  'R3'),
  ('Microprocessor Lab',   'Sensor',       'DHT11'),
  ('Microprocessor Lab',   'Sensor',       'HC-SR04');

-- ১২টা আসল ইউনিট। material_id হাতে না লিখে নাম দিয়ে খুঁজে নিচ্ছি,
-- কারণ id গুলো AUTO_INCREMENT — আগে থেকে ধরে নেওয়া ঠিক নয়।
INSERT INTO equipment_units (material_id, serial_no, status)
SELECT m.id, u.serial_no, u.status
FROM materials m
JOIN (
    SELECT 'Breadboard'   AS name, '830 points' AS variant, 'BB-001'  AS serial_no, 'AVAILABLE' AS status UNION ALL
    SELECT 'Breadboard',   '830 points', 'BB-002',  'AVAILABLE' UNION ALL
    SELECT 'Breadboard',   '830 points', 'BB-003',  'DAMAGED'   UNION ALL
    SELECT 'Logic IC',     '7400 NAND',  'IC-7400-A', 'AVAILABLE' UNION ALL
    SELECT 'Logic IC',     '7400 NAND',  'IC-7400-B', 'AVAILABLE' UNION ALL
    SELECT 'Jumper Wires', 'Male-Male',  'JW-001',  'AVAILABLE' UNION ALL
    SELECT 'Arduino Uno',  'R3',         'ARD-001', 'AVAILABLE' UNION ALL
    SELECT 'Arduino Uno',  'R3',         'ARD-002', 'AVAILABLE' UNION ALL
    SELECT 'Arduino Uno',  'R3',         'ARD-003', 'AVAILABLE' UNION ALL
    SELECT 'Sensor',       'DHT11',      'DHT-001', 'AVAILABLE' UNION ALL
    SELECT 'Sensor',       'DHT11',      'DHT-002', 'AVAILABLE' UNION ALL
    -- HC-SR04 এর একটাও AVAILABLE ইউনিট নেই — "0 available" স্ক্রিন টেস্ট করার জন্য।
    SELECT 'Sensor',       'HC-SR04',    'US-001',  'DAMAGED'
) u ON u.name = m.name AND u.variant = m.variant;
