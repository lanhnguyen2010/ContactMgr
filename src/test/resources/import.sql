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

-- Password of user is Password1@
insert into users (id, username, password, first_name, last_name, role, expired_date, active, email, language) values (1, 'user1', '6964f527f011df8756f87c3e8a76884f','fistname1', 'lastname1', 'ADMINISTRATOR', '2020-01-01', 1, 'emai1@abc', 'en');
insert into users (id, username, password, first_name, last_name, role, expired_date, active, email, language) values (2, 'user2', '6964f527f011df8756f87c3e8a76884f','fistname2', 'lastname2', 'DESIGNER', '2020-01-01', 1, 'emai2@abc', 'en');
insert into users (id, username, password, first_name, last_name, role, expired_date, active, email, language) values (3, 'user3', '6964f527f011df8756f87c3e8a76884f','fistname3', 'lastname3', 'EDITOR', '2020-01-01', 1, 'emai3@abc', 'vi');

insert into user_assignedcompanies (user_id, company_id) values (2,1);
insert into user_assignedcompanies (user_id, company_id) values (2,2);
insert into user_assignedcompanies (user_id, company_id) values (3,1);
insert into user_assignedcompanies (user_id, company_id) values (3,2);
