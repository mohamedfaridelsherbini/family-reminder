CREATE TABLE locations (
  id VARCHAR(40) PRIMARY KEY,
  user_id VARCHAR(40) NOT NULL REFERENCES users(id),
  label VARCHAR(120) NOT NULL,
  lat DOUBLE PRECISION NOT NULL,
  lng DOUBLE PRECISION NOT NULL,
  radius_meters INT NOT NULL
);

CREATE TABLE reminders (
  id VARCHAR(40) PRIMARY KEY,
  note_id VARCHAR(40) NOT NULL REFERENCES notes(id),
  type VARCHAR(16) NOT NULL,
  trigger_at TIMESTAMPTZ NULL,
  location_id VARCHAR(40) NULL REFERENCES locations(id)
);

CREATE INDEX idx_reminders_note ON reminders(note_id);
