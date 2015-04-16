CREATE TABLE image (
  id INT NOT NULL AUTO_INCREMENT,
  pathFull VARCHAR(256), 
  fileName VARCHAR(100),  
  content_type VARCHAR (90),
  created_by VARCHAR(20),
  created_at TIMESTAMP,
  updated_by VARCHAR(20),
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);