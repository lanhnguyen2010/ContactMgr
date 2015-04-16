CREATE TABLE countries (
    country_code VARCHAR(3),
    country VARCHAR(100),
    PRIMARY KEY (country_code)
);

INSERT INTO countries(country_code, country) VALUES('VN', 'Việt Nam');
INSERT INTO countries(country_code, country) VALUES('US', 'United States');
INSERT INTO countries(country_code, country) VALUES('LA', 'Laos');
INSERT INTO countries(country_code, country) VALUES('KH', 'Cambodia');
INSERT INTO countries(country_code, country) VALUES('JP', 'Japan');
INSERT INTO countries(country_code, country) VALUES('FR', 'France');