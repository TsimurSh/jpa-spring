--  ROLES
INSERT INTO role (role)
VALUES ('SUPER_ADMIN');
INSERT INTO role (role)
VALUES ('ADMIN');
INSERT INTO role (role)
VALUES ('SELLER');
INSERT INTO role (role)
VALUES ('BUYER');

-- SUBSCRIPTIONS
INSERT INTO subscription (name, title, price)
VALUES ('INDIVIDUAL', 'Individual plan', 15);
INSERT INTO subscription (name, title, price)
VALUES ('FAMILY', 'Family plan', 25);
INSERT INTO subscription (name, title, price)
VALUES ('GROUP', 'Group plan', 35);
