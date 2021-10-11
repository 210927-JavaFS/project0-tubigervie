CREATE TABLE logins (
	user_id SERIAL PRIMARY KEY,
	user_name VARCHAR(50) NOT NULL,
	user_pass VARCHAR(50) NOT NULL,
	acc_type VARCHAR(20) NOT NULL
);

CREATE TABLE standard_users(
	user_id INTEGER REFERENCES logins(user_id) PRIMARY KEY,
	inventory VARCHAR(800),
	decks VARCHAR(100)
);

CREATE TABLE decks(
	deck_id SERIAL PRIMARY KEY,
	user_id INTEGER REFERENCES standard_users(user_id),
	deck_name VARCHAR(50),
	card_list VARCHAR(50),
	card_count INTEGER
);

DROP TABLE IF EXISTS decks;

CREATE OR REPLACE FUNCTION populate_standard_users() RETURNS TRIGGER AS 
$BODY$
BEGIN 
	IF NEW.acc_type = 'standard' THEN
		INSERT INTO standard_users (user_id, inventory, decks) 
		VALUES(NEW.user_id, NULL, NULL); 
	END IF;
	RETURN NEW;
END
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER populate_standard_users AFTER INSERT ON logins
		FOR EACH ROW 
		EXECUTE PROCEDURE populate_standard_users();
		
INSERT INTO logins(user_name, user_pass, acc_type)
	VALUES('etubig', 'helloworld', 'standard');
	
INSERT INTO logins(user_name, user_pass, acc_type)
	VALUES('adminlog', 'adminpass', 'admin');
	
TRUNCATE TABLE standard_users RESTART IDENTITY CASCADE;