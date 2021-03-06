--CREATE DATABASE project1

DROP TABLE IF EXISTS ers_user_roles CASCADE;
DROP TABLE IF EXISTS ers_users CASCADE;
DROP TABLE IF EXISTS ers_reimbursement_type CASCADE;
DROP TABLE IF EXISTS ers_reimbursement_status CASCADE;
DROP TABLE IF EXISTS ers_reimbursement;


--QUESTION: Do the red dots in the Project description mean that the fields must be set to NOT NULL?

--First create the 5 tables defined in the Project1 README file
CREATE TABLE ers_user_roles (
	ers_user_role_id SERIAL PRIMARY KEY,
	user_role VARCHAR(20) NOT NULL);

--based on the difficulties I had with encrypting passwords in project 0, I've decided to use a byte array for the PASSWORD
--here which will allow for the transfer of raw bytes for easier encryption

--rationale for FK actions of ers_users table:
/*
 * if for whatever reason a user role is deleted from the database (whether accidentally or on purpose) we don't want to delete all users of that type
 * from the database. Instead, we create a "default" user type which won't have any permissions and set the specific user role to this type
 */
CREATE TABLE ers_users (
	ers_users_id SERIAL PRIMARY KEY,
	ers_username VARCHAR(50) UNIQUE NOT NULL,
	ers_password BYTEA NOT NULL,
	user_first_name VARCHAR(100) NOT NULL,
	user_last_name VARCHAR(100) NOT NULL,
	user_email VARCHAR(150) UNIQUE NOT NULL,
	user_role_id INTEGER NOT NULL REFERENCES ers_user_roles(ers_user_role_id)
		ON DELETE SET DEFAULT);

	--set the default value for our user_role_id in case of role deletion
ALTER TABLE ers_users ALTER COLUMN user_role_id SET DEFAULT 1;

CREATE TABLE ers_reimbursement_type (
	reimb_type_id SERIAL PRIMARY KEY,
	reimb_type VARCHAR(10) NOT NULL);

CREATE TABLE ers_reimbursement_status (
	reimb_status_id SERIAL PRIMARY KEY,
	reimb_status VARCHAR(10) NOT NULL);

--I'm going to allow the submitted timestamp to be blank here because I want functionality for the employee
--to save their reimbursement request and potentially edit it before submittal

--rationale for FK actions of ers_reimbursement table:
/*
 * Data Deletion-
 * if an employee is deleted from the database (aka got fired or quit) then there's no longer a need for them to be able to
 * check on their reimbursement receipts. Because of this we cascade the delete here. On the flip side, if the person who
 * approved the imbursement leaves the company we don't want to delete the record for the employee still here as they may want to
 * view it still. In this case we set the approver column to a value of "ex-employee". As for the status and type id's, there's no
 * reason that if those were deleted from their tables that it should delete entries in the reimbursement table. For historical resasons
 * just leave that data in the table un-altered
 *
 * Data Update-
 * There's no real reason that the id number primary keys should be altered in their original tables, but for whatever reason if they are
 * then cascade those updates to this table as well.
 */
CREATE TABLE ers_reimbursement (
	reimb_id SERIAL PRIMARY KEY,
	reimb_amount NUMERIC(7,2) NOT NULL,
	reimb_submitted TIMESTAMP,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_receipt BYTEA,
	reimb_author INTEGER NOT NULL REFERENCES ers_users(ers_users_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	reimb_resolver INTEGER REFERENCES ers_users(ers_users_id)
		ON UPDATE CASCADE
		ON DELETE SET DEFAULT,
	reimb_status_id INTEGER NOT NULL REFERENCES ers_reimbursement_status(reimb_status_id)
		ON UPDATE CASCADE
		ON DELETE NO ACTION,
	reimb_type_id INTEGER NOT NULL REFERENCES ers_reimbursement_type(reimb_type_id)
		ON UPDATE CASCADE
		ON DELETE NO ACTION);

--SET the default value for the approver column to "ex-employee" which has an id of 2
ALTER TABLE ers_reimbursement  ALTER COLUMN reimb_resolver SET DEFAULT 2;

--Now that the tables are created, insert some basic data into them

--first create a few basic reimbursement types (for now just use examples from project write up)
INSERT INTO ers_reimbursement_type (reimb_type) VALUES ('lodging'),
	('food'),
	('travel'),
	('other');

--next create the different status for a reimbursement request
INSERT INTO ers_reimbursement_status (reimb_status) VALUES ('created'),
	('submitted'),
	('pending'),
	('approved'),
	('denied');

--now create a few basic employee types
INSERT INTO ers_user_roles (user_role) VALUES ('default'),
	('ex-employee'),
	('finance manager'),
	('manager'),
	('finance analyst'),
	('engineer'),
	('intern');

--create a few users of each of the above user types
/*
 * Here's a list of all of the decrypted passwords for the below users
 * rfloyd01 = Coding_is_Kewl34
 * sno19 = Guitar_man12
 * danthaman = 12345_ABcde
 * jjmm07 = EggsInMyDr@nk89
 * bilbo24 = Baller4Life};
 * jstarita = M0xLotus@2
 * Ir0nMan = Mind0nMyM@ney
 */
INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) VALUES
	('rfloyd01', E'\\x005C0088007D00820087008000780082008C00780064007E00900085004C004D', 'Robert', 'Floyd', 'robert.floyd@airproducts.com', 3),
	('sno19', E'\\x0060008E0082008D007A008B00780086007A0087004A004B', 'Scott', 'Olsen', 'scott.olsen@gairproducts.com', 3),
	('danthaman', E'\\x004A004B004C004D004E0078005A005B007C007D007E', 'Dan', 'Preuss', 'dannyboy12@airproducts.com', 4),
	('jjmm07', E'\\x005E00800080008C0062008700660092005D008B00590087008400510052', 'Jonathan', 'Miller', 'jmills7@airproducts.com', 4),
	('bilbo24', E'\\x005B007A00850085007E008B004D00650082007F007E00960054', 'Billy', 'Floyd', 'wof31n@airproducts.com', 5),
	('jstarita', E'\\x00660049009100650088008D008E008C0059004B', 'Jake', 'Astarita', 'jake.astarita@airproducts.com', 6),
	('Ir0nMan', E'\\x006600820087007D0049008700660092006600590087007E0092', 'Tony', 'Stark', 'tony.stark@airproducts.com', 7);


--finally add a few reimbursement requests so we can try to process them in the Java application
INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_receipt, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id) VALUES
	(125.12, '2022-01-08 16:05:06.445', null, 'took client out for dinner', null, 4, null, 2, 2),
	(1000.00, '2022-01-12 10:33:45.123', null, 'lodging for a week at holiday inn', null, 6, null, 2, 1),
	(15.28, null, null, 'needed my smokes', null, 4, null, 1, 4);

 --Here are some test lines I wrote and commented out to make sure everything was working as intended

--This was to test that a users role was set to default if their existing role was accidentally deleted
--DELETE FROM ers_user_roles WHERE ers_user_role_id = 3;

--This line was to confirm that creating a user without a set role would give them the default user_role_id
/*
 * INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email) VALUES
 * 	('Ir0nMan2', 'Mind0nMyM@ney', 'tony', 'stark', 'tony.stark2@airproducts.com');
 */

--DELETE FROM ers_users WHERE ers_users_id = 4;