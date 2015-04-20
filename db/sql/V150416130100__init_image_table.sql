CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT,
  path_full VARCHAR(255), 
  file_name VARCHAR(100),  
  content_type VARCHAR (10),
  created_at TIMESTAMP,
  PRIMARY KEY(id)
);

INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'ky', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'tho', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'lanh', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'diu', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'thoai', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'nhan', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'tran', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'trieu', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'doan', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'thanh', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'tuong', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'nghia', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'ngoc', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'loan', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'huan', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'tan', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'tu', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'long', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'linh', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'ly', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'nga', '.jpeg');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'hoa', '.png');
INSERT INTO images(path_full, file_name, content_type) VALUES('etc/photos', 'huong', '.jpeg');
