CREATE TABLE company (
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
    country VARCHAR(2),
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
    gender VARCHAR(1),
    birthday DATE,
    photo VARCHAR(255),
    addr_line1 VARCHAR(255),
    addr_line2 VARCHAR(255),
    city VARCHAR(150),
    state VARCHAR(150),
    postal_code INT,
    country VARCHAR(2),
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
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `FIRST_NAME` varchar(45) DEFAULT NULL,
  `LAST_NAME` varchar(45) DEFAULT NULL,
  `ROLE` enum('ADMINISTRATOR','DESIGNER','EDITOR') DEFAULT NULL,
  `EXPIRED_DATE` date DEFAULT NULL,
  `ACTIVE` int(11) DEFAULT NULL,
  `CREATED_AT` date DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `UPDATED_AT` date DEFAULT NULL,
  `UPDATED_BY` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `LANGUAGE` enum('English','Vietnamese') DEFAULT 'English',
  `ASSIGNEDCOMPANIES` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;