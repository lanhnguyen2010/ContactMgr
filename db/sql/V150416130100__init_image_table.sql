CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT,
  path_full VARCHAR(256), 
  file_name VARCHAR(100),  
  content_type VARCHAR (90),
  created_at TIMESTAMP,
  PRIMARY KEY(id)
);

INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'ky', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'tho', '.jpge');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'lanh', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'diu', '.jpge');
