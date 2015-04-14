CREATE TABLE greetings (
  id INT NOT NULL AUTO_INCREMENT,
  code VARCHAR(2) NOT NULL,
  message VARCHAR(64) NOT NULL,
  created_by VARCHAR(20),
  created_at TIMESTAMP,
  updated_by VARCHAR(20),
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

INSERT INTO greetings(code, message) VALUES('en', 'Hello');
INSERT INTO greetings(code, message) VALUES('fr', 'Bonjour');
INSERT INTO greetings(code, message) VALUES('vi', 'Xin chào');
INSERT INTO greetings(code, message) VALUES('ja', 'おはよう');