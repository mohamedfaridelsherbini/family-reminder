CREATE TABLE users (
  id VARCHAR(40) PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE families (
  id VARCHAR(40) PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  owner_id VARCHAR(40) NOT NULL REFERENCES users(id)
);

CREATE TABLE memberships (
  user_id VARCHAR(40) NOT NULL REFERENCES users(id),
  family_id VARCHAR(40) NOT NULL REFERENCES families(id),
  role VARCHAR(16) NOT NULL,
  PRIMARY KEY (user_id, family_id)
);
