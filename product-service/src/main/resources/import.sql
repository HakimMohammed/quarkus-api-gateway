INSERT INTO product(id, name, price, description) VALUES (1, 'Produit A', 10, 'Description du produit A');
INSERT INTO product(id, name, price, description) VALUES (2, 'Produit C', 30, 'Description du produit C');
INSERT INTO product(id, name, price, description) VALUES (3, 'Produit B', 20, 'Description du produit B');

-- Start IDS from 4
ALTER SEQUENCE Product_SEQ RESTART WITH 4;