CREATE TABLE notes (
  id VARCHAR(40) PRIMARY KEY,
  family_id VARCHAR(40) NOT NULL REFERENCES families(id),
  title VARCHAR(120) NOT NULL,
  body TEXT NULL,
  tags TEXT NOT NULL DEFAULT '',
  due_at TIMESTAMPTZ NULL
);

CREATE INDEX idx_notes_family ON notes(family_id);
