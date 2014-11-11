----
-- Note: professor said primary keys are not necessary since these are simple tutorial tables.
----

----------------------
-- MERCHANDISE TABLE
----------------------
CREATE TABLE MERCHANDISE(
   name VARCHAR(25),
   price DECIMAL (20,2), -- dollars and cents
   description VARCHAR(200)
);

----------------------
-- EMPLOYEE TABLE
----------------------
CREATE TABLE EMPLOYEE(
   fname VARCHAR(25),
   lname VARCHAR(25),
   address VARCHAR(50),
   city VARCHAR(25),
   state VARCHAR(25),
   zipcode VARCHAR(25), -- string type if hyphens are present
   gender VARCHAR(6) -- 'male' or 'female'
);

----------------------
-- CUSTOMER TABLE
----------------------
CREATE TABLE CUSTOMER(
   fname VARCHAR(25),
   lname VARCHAR(25),
   address VARCHAR(50),
   city VARCHAR(25),
   state VARCHAR(25),
   zipcode VARCHAR(25), -- string type if hyphens are present
   gender VARCHAR(6) -- 'male' or 'female'
);
