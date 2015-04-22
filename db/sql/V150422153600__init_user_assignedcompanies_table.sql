DROP TABLE IF EXISTS `user_assignedcompanies`;
create table user_assignedcompanies(
    user_id INT,
    company_id INT,
    PRIMARY KEY (user_id, company_id)
);

ALTER TABLE user_assignedcompanies
ADD CONSTRAINT fk_user_assignedcompanies_user
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE user_assignedcompanies
ADD CONSTRAINT fk_user_assignedcompanies_company
FOREIGN KEY (company_id)
REFERENCES companies(id);