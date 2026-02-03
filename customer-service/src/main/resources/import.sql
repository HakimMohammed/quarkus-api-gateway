INSERT INTO client (id, name, email, address) VALUES (1, 'Client A', 'clienta@example.com', 'Addresse du client A');
INSERT INTO client (id, name, email, address) VALUES (2, 'Client B', 'clientb@example.com', 'Addresse du client B');
INSERT INTO client (id, name, email, address) VALUES (3, 'Client C', 'clientc@example.com', 'Addresse du client C');

ALTER SEQUENCE CLIENT_SEQ RESTART WITH 4;