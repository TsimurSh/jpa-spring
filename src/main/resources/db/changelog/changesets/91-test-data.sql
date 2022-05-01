---------------
-- TEST DATA --
---------------

--------
-- USERS
--------

---- Bosses:
INSERT INTO user_account(id, name, surname, balance, age, email, password, last_payment, employer_nip,
                         subscription_name)
VALUES (-5, 'Tsima', 'Batman', 770, 32, 'man@bat.man', 'Secret_password_1', null, null, null),
       (-6, 'Super', 'Man', 20, 44, 'wooman@mann.com', 'Secret_password_6', '2022-02-22 16:20:42.000000', null,
        'GROUP'),
       (-3, 'Slava', 'Kaktus', 850, 44, 'woman@bat.com', 'Secret_password_2', null, null, null),
       (-1, 'Arsenij', 'Spiderman', 7, 88, 'mango@bat.ua', 'Secret_password_3', '2022-04-20 16:20:42.000000', null,
        'INDIVIDUAL');

---- Employees:
INSERT INTO user_account(id, name, surname, balance, age, email, password, last_payment, employer_nip,
                         subscription_name)
VALUES (-4, 'Dima', 'Koltun', 950, 23, 'man@dat.io', 'Secret_password_4', null, null, null),
       (-2, 'Lena', 'Golovach', 1650, 67, 'many@bat.pl', 'Secret_password_5', '2022-03-20 12:47:25.000000', null,
        'FAMILY');

----------------
-- ORGANIZATIONS
----------------
INSERT INTO organization (nip, name, address, owner_id)
VALUES (2345678, 'Mercedes', 'ul.Pobeda, Munich', -3),
       (3456789, 'Oracle', 'ul.Drakuly, Bermudas', -5),
       (1234567, 'Apollo', 'ul. Dwornikow, Poznan', -1);

-- Employers of Organizations
UPDATE user_account
SET employer_nip= 3456789
WHERE id = -4;
UPDATE user_account
SET employer_nip= 1234567
WHERE id = -2;


-- USER'S ROLES
INSERT INTO user_role (user_id, role)
VALUES (-5, 'SELLER'),
       (-5, 'ADMIN'),
       (-5, 'SUPER_ADMIN'),
       (-3, 'SELLER'),
       (-2, 'BUYER'),
       (-1, 'BUYER');

