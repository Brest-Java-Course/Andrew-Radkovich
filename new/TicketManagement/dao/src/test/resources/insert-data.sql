DELETE FROM CUSTOMER;
DELETE FROM TICKET;

INSERT INTO CUSTOMER (customer_id, name, identificationNumber) VALUES( 1, 'John Doe', 'AB1' );
INSERT INTO CUSTOMER (customer_id, name, identificationNumber) VALUES( 2, 'Albus Dumbldore', 'AB2' );
INSERT INTO CUSTOMER (customer_id, name, identificationNumber) VALUES( 3, 'Jack Sparrow', 'AB3' );
INSERT INTO CUSTOMER (customer_id, name, identificationNumber) VALUES( 4, 'Fox Mulder', 'AB4' );

INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (1, 1, 2300, 1, '2014-3-1', 'interstellar', TRUE);
INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (2, 2, 2300, 1, '2014-3-2', '1408', TRUE);
INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (3, 3, 2300, 1, '2014-3-3', 'interstellar', TRUE);
INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (4, 4, 2300, 2, '2014-3-1', 'interstellar', TRUE);
INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (5, 1, 2300, 3, '2014-3-1', 'interstellar', TRUE);
INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (6, 2, 2300, 4, '2014-3-15', 'green mile', TRUE);

INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (7, NULL, 2300, 4, '2014-3-15', 'green mile', FALSE);
INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (8, NULL, 2300, 4, '2014-3-15', 'Star Wars : New Hope', FALSE);
INSERT INTO TICKET (ticket_id, customer_id, cost, location, data, title, taken) VALUES (9, NULL, 2300, 4, '2014-3-15', 'interstellar', FALSE);