CREATE TABLE IF NOT EXISTS CUSTOMER
(
customer_id BIGINT IDENTITY,
name VARCHAR(25),
identificationNumber VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS TICKET
(
ticket_id BIGINT IDENTITY,
customer_id BIGINT,
cost INTEGER,
location INTEGER,
data DATE,
title VARCHAR(50),
taken BOOLEAN,
CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES CUSTOMER (customer_id)
);