CREATE DATABASE CONTACTMGR;
USE CONTACTS_MANAGER;

CREATE TABLE IF NOT EXISTS CONTACT(
 ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 FIRST_NAME VARCHAR(50) NOT NULL,
 MIDDLE_NAME VARCHAR(50),
 LAST_NAME VARCHAR(50) NOT NULL,
 EMAIL VARCHAR(255) UNIQUE,
 PHOTO VARCHAR(255), -- STORE URL OF PHOTO
 MOBILE VARCHAR(15),
 GENDER VARCHAR(10),
 BIRTHDAY TIMESTAMP,
 ADDR_LINE1 VARCHAR(250),
 ADDR_LINE2 VARCHAR(250),
 CITY VARCHAR(150),
 STATE VARCHAR(150),
 POSTAL_CODE INTEGER(5),
 COUNTRY VARCHAR(150),
 PHONE VARCHAR(15),
 FAX VARCHAR(20),
 TITLE VARCHAR(50),
 DEPARTMENT VARCHAR(100),
 COMPANY_ID INT,
 CREATED_AT TIMESTAMP,
 CREATED_BY VARCHAR(50),
 UPDATED_AT TIMESTAMP,
 UPDATED_BY VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS COMPANY(
 ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 CODE VARCHAR(10),
 NAME VARCHAR(100) NOT NULL,
 WEBSITE VARCHAR(255),
 LOGO VARCHAR(255),
 MESSAGE VARCHAR(255),
 ADDR_LINE1 VARCHAR(250),
 ADDR_LINE2 VARCHAR(250),
 CITY VARCHAR(150),
 STATE VARCHAR(150),
 POSTAL_CODE INTEGER(5),
 COUNTRY VARCHAR(150),
 PHONE VARCHAR(15) UNIQUE,
 FAX VARCHAR(20) UNIQUE,
 CREATED_AT TIMESTAMP,
 CREATED_BY VARCHAR(50),
 UPDATED_AT TIMESTAMP,
 UPDATED_BY VARCHAR(50)
);

ALTER TABLE CONTACT
ADD CONSTRAINT FK_CONTACT_COMPANY
FOREIGN KEY  (COMPANY_ID)
REFERENCES COMPANY(ID);


INSERT INTO COMPANY VALUES(1, 'AB','KMS Technology','kms-technology.com','photo/logokms.img','trust','Trường Sơn',null,'Tp.Hồ Chí Minh',null,null,'USA','0123456789','87-123456789',null,null,null,null);
INSERT INTO COMPANY VALUES(2, 'CD','FPT Soft','fpt.com','photo/logofpt.img','abc',null,null,'Tp.Hồ Chí Minh',null,null,'VN','0123456788','87-123456788',null,null,null,null);
INSERT INTO COMPANY VALUES(3, 'EF','NTT Data','nttdata.com','photo/logonttdata.img','trust',null,null,'Tp.Hồ Chí Minh',null,null,'VN','0123456787','87-123456787',null,null,null,null);

INSERT INTO CONTACT VALUES(1,'Trang','Thu','Biện','thutrang2293@gmail.com','photo/1.img','01668889999','Female','1993-02-02','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'Việt Nam',null,null,null,null,1,null,null,null,null);
INSERT INTO CONTACT VALUES(2,'Trang','Thuỳ','Nguyễn','trangnguyen@gmail.com','photo/2.img','01668889998','Female','1993-05-02','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'Việt Nam',null,null,null,null,1,null,null,null,null);
INSERT INTO CONTACT VALUES(3,'Trang','Bảo','Trần','baotrang@gmail.com','photo/3.img','01668889997','Female','1993-02-05','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'Việt Nam',null,null,null,null,3,null,null,null,null);
INSERT INTO CONTACT VALUES(4,'Thái','Hoàng','Lâm','lamhoangthai@gmail.com','photo/4.img','01668889996','Male','1993-03-02','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'Việt Nam',null,null,null,null,2,null,null,null,null);
INSERT INTO CONTACT VALUES(5,'Nhật','Minh','Nguyễn','nhatnguyen@gmail.com','photo/5.img','01668889995','Male','1993-02-11','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'Việt Nam',null,null,null,null,2,null,null,null,null);
INSERT INTO CONTACT VALUES(6,'Hương','Lan','Ngô','huongngo@gmail.com','photo/6.img','01668889994','Female','1993-12-02','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'Việt Nam',null,null,null,null,3,null,null,null,null);
