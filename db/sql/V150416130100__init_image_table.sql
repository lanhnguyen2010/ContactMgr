CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT,
  path_full VARCHAR(255),
  file_name VARCHAR(100),
  content_type VARCHAR (10),
  created_at TIMESTAMP,
  PRIMARY KEY(id)
);
