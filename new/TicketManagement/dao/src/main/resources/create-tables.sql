CREATE TABLE IF NOT EXISTS CUSTOMER
(
customer_id BIGINT IDENTITY,
name VARCHAR(25),
identificationNumber VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS TICKET
(
ticket_id BIGINT,
customer_id BIGINT,
cost INTEGER,
location INTEGER,
data DATE,
title VARCHAR(50),
CONSTRAINT pk_ticket PRIMARY KEY (ticket_id, customer_id),
CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES CUSTOMER (customer_id)
);