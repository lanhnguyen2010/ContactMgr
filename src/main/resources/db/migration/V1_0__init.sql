CREATE TABLE IF NOT EXISTS CONTACT(
ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
FIRST_NAME VARCHAR(50) NOT NULL,
MIDDLE_NAME VARCHAR(50),
LAST_NAME VARCHAR(50),
EMAIL VARCHAR(255),
PHOTO TEXT, -- STORE URL OF PHOTO
MOBILE CHAR(15),
GENDER VARCHAR(10),
BIRTHDAY DATE,
STREET VARCHAR(250),
CITY VARCHAR(150),
STATE VARCHAR(150),
POSTAL_CODE integer(5),
COUNTRY VARCHAR(150),
PHONE CHAR(15),
FAX CHAR(20),
TITLE VARCHAR(50),
DEPARTMENT VARCHAR(100),
COMPANY_ID INT NOT NULL
);

CREATE TABLE IF NOT EXISTS COMPANY(
ID INT NOT NULL auto_increment PRIMARY KEY,
CODE CHAR(10),
NAME VARCHAR(100) NOT NULL,
WEBSITE TEXT,
LOGO TEXT,
MESSAGE TEXT,
STREET VARCHAR(250),
CITY VARCHAR(150),
STATE VARCHAR(150),
POSTAL_CODE integer(5),
COUNTRY VARCHAR(150),
PHONE CHAR(15) UNIQUE,
FAX CHAR(20) UNIQUE
);

ALTER TABLE CONTACT
ADD CONSTRAINT FK_CONTACT_COMPANY
FOREIGN KEY  (COMPANY_ID)
REFERENCeS COMPANY(ID);
