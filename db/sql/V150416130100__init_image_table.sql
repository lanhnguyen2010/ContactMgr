CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT,
  path_full VARCHAR(256), 
  file_name VARCHAR(100),  
  content_type VARCHAR (90),
  created_by VARCHAR(20),
  created_at TIMESTAMP,
  updated_by VARCHAR(20),
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);