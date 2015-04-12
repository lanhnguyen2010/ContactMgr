﻿CREATE TABLE company (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100),
  website VARCHAR(255),
  logo VARCHAR(255),
  message VARCHAR(255),
  addr_line1 VARCHAR(255),
  addr_line2 VARCHAR(255),
  city VARCHAR(150),
  state VARCHAR(150),
  postal_code INT,
  country CHAR(2),
  phone VARCHAR(15),
  fax VARCHAR(15),
  created_by VARCHAR(20),
  created_at TIMESTAMP,
  updated_by VARCHAR(20),
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

CREATE TABLE contact (
  id INT NOT NULL AUTO_INCREMENT,
  display_name VARCHAR(150) NOT NULL,
  first_name VARCHAR(50),
  middle_name VARCHAR(50),
  last_name VARCHAR(50),
  email VARCHAR(255),
  mobile VARCHAR(15),
  gender CHAR(1),
  birthday DATE,
  addr_line1 VARCHAR(255),
  addr_line2 VARCHAR(255),
  city VARCHAR(150),
  state VARCHAR(150),
  postal_code INT,
  country CHAR(2),
  phone VARCHAR(15),
  fax VARCHAR(15),
  job_title VARCHAR(50),
  department VARCHAR(100),
  company_id INT,
  created_by VARCHAR(20),
  created_at TIMESTAMP,
  updated_by VARCHAR(20),
  updated_at TIMESTAMP,
  PRIMARY KEY(id)
);

ALTER TABLE contact
ADD CONSTRAINT fk_contact_company
FOREIGN KEY (company_id)
REFERENCES company(id);
