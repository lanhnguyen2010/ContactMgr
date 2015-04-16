CREATE TABLE images (
  id INT NOT NULL AUTO_INCREMENT,
  pathFull VARCHAR(256), 
  fileName VARCHAR(100),  
  contentType VARCHAR (90),
  PRIMARY KEY(id)
);