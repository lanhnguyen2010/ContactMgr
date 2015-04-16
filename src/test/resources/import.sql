insert into greetings(code, message, created_at) values('en', 'hello', parsedatetime('15-04-01','yy-MM-dd'));
insert into greetings(code, message, created_at) values('fr', 'bonjour', parsedatetime('15-04-02','yy-MM-dd'));
insert into greetings(code, message, created_at) values('vi', 'xin chào', parsedatetime('15-04-03','yy-MM-dd'));
insert into greetings(code, message, created_at) values('ja', 'おはよう', parsedatetime('14-05-04','yy-MM-dd'));

insert into companies(id, name, website, addr_line1, city) values(1, 'name1','website1','addr11', 'city1');
insert into companies(id, name, website, addr_line1, city) values(2, 'name2','website2','addr12', 'city2');
insert into companies(id, name, website, addr_line1, city) values(3, 'name3','website3','addr13', 'city3');

insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(1, 'display_name1', 'first_name1', 'last_name1', 'email1', 'mobile1', 'addr11', 'job_title1', 1);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(2, 'display_name2', 'first_name2', 'last_name2', 'email2', 'mobile2', 'addr12', 'job_title2', 2);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(3, 'display_name3', 'first_name3', 'last_name3', 'email3', 'mobile3', 'addr13', 'job_title3', 3);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(4, 'display_name4', 'first_name1', 'last_name1', 'email1', 'mobile1', 'addr11', 'job_title1', 1);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(5, 'display_name5', 'first_name2', 'last_name2', 'email2', 'mobile2', 'addr12', 'job_title2', 2);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(6, 'display_name6', 'first_name3', 'last_name3', 'email3', 'mobile3', 'addr13', 'job_title3', 3);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(7, 'display_name7', 'first_name1', 'last_name1', 'email1', 'mobile1', 'addr11', 'job_title1', 1);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(8, 'display_name8', 'first_name2', 'last_name2', 'email2', 'mobile2', 'addr12', 'job_title2', 2);
insert into contacts(id, display_name, first_name, last_name, email, mobile, addr_line1, job_title, company_id) values(9, 'display_name9', 'first_name3', 'last_name3', 'email3', 'mobile3', 'addr13', 'job_title3', 3);

insert into company values(1,'KMS Technology','kms-technology.com','photo/logokms.img','trust','Trường Sơn',null,'Tp.Hồ Chí Minh',null,null,'US','+84 123456789','87-123456789',null,null,null,null);

insert into contact values(1,'Trang','Trang','Thu','Biện','thutrang2293@gmail.com','+84 1668889999','F','1993-02-02','photo/1.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(2,'Trang','Trang','Thuỳ','Nguyễn','trangnguyen@gmail.com','+84 1668889998','F','1993-05-02','photo/2.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(3,'Trang','Trang','Bảo','Trần','baotrang@gmail.com','+84 1668889997','F','1993-02-05','photo/3.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(4,'Thai','Thái','Hoàng','Lâm','lamhoangthai@gmail.com','+84 1668889996','M','1993-03-02','photo/4.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(5,'Minh Nhật','Nhật','Minh','Nguyễn','nhatnguyen@gmail.com','+84 1668889995','M','1993-02-11','photo/5.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(6,'lan Hương','Hương','Lan','Ngô','huongngo@gmail.com','+84 1668889994','F','1993-12-02','photo/6.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(7,'Minh Triều','Trieu','Minh','Đoàn','minhtrieudoan@gmail.com','+84 1668989999','F','1993-02-02','photo/7.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(8,'Anh Thoại','Thoai','Anh','Tran','thoaitran@gmail.com','+84 1668899999','M','1993-02-02','photo/8.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(9,'Hoàng Diệu','Diệu',null,'Lê','dieule@gmail.com','+84 1660889999','M','1993-02-02','photo/9.img','Ngô Gia Tự',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(10,'Đại Nhân','Nhan','Dai','Tran','nhandaitran@gmail.com','+84 1668889989','M','1990-08-02','photo/10.img','Hoàng Văn Thụ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(11,'Phi Phú','Phú',null,'Hà','phuha@gmail.com','+84 1668889979','M','1991-07-02','photo/11.img','Nguyễn Văn Cừ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(12,'Thiên Thọ','Thọ','Thiên','Ông','ongtho2293@gmail.com','+84 1668889969','M','1990-11-02','photo/12.img','Ngô Quyền',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(13,'Châu Kỳ','Kỳ',null,'Nguyễn','kynguyen@gmail.com','+84 1668889959','M','1989-12-12','photo/13.img','Trường Chinh',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(14,'Trâm','Trâm','Thu','Nguyễn','tramnguyen@gmail.com','+84 1668889949','M','1990-09-02','photo/14.img','Cách Mạng Tháng 8',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(15,'Ánh Tuyết','Tuyết','Ánh','Trần','anhtuyet@gmail.com','+84 1668789919','F','1983-11-02','photo/15.img','Nguyễn Biểu',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(16,'Hoàng Anh','Anh','Hoàng','Nguyễn','hoangnguyen@gmail.com','+84 1668889929','M','1983-09-12','photo/16.img','Hoàng Văn Thụ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(17,'Thu Hiền','Hiền','Thu','Trần','thuhien@gmail.com','+84 1668889919','F','1983-12-02','photo/17.img','Hoàng Văn Thụ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(18,'Trúc Thanh','Thanh','Ngọc','Bạch','thanhbach@gmail.com','+84 1668889909','F','1993-02-02','photo/18.img','Hoàng Văn Thụ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(19,'Thiên Thanh','Thiên','Thanh','Ngô','thanhngo@gmail.com','+84 1668889908','F','1993-02-02','photo/19.img','Nguyễn Văn Cừ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(20,'Tuyết Hoa','Tuyết','Hoa','Nguyễn','hoanguyen@gmail.com','+84 1668889907','F','1993-02-02','photo/20.img','Nguyễn Văn Cừ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(21,'Bảo Ngọc','Ngọc','Bảo','Lâm','baolam@gmail.com','+84 1668889906','F','1993-02-02','photo/21.img','Nguyễn Văn Cừ',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(22,'Như Mai','Mai','Như','Nguyễn','mainguyen@gmail.com','+84 1668889905','F','1993-02-02','photo/22.img','Trường Chinh',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(23,'Thu Thảo','Thảo','Thu','Nguyễn','thaonguyen@gmail.com','+84 1668889904','F','1993-02-02','photo/23.img','Trường Chinh',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);
insert into contact values(24,'Thái Y Lâm','Lâm','Y','Thái','thaiylam@gmail.com','+84 1668889903','F','1993-02-02','photo/24.img','Trường Chinh',null,'Tp.Hồ Chí Minh',null,null,'VN',null,null,null,null,1,null,null,null,null);

