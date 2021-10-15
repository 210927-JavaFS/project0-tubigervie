CREATE TABLE logins (
	user_id SERIAL PRIMARY KEY,
	user_name VARCHAR(50) NOT NULL,
	user_pass VARCHAR(70) NOT NULL,
	acc_type VARCHAR(20) NOT NULL
);

CREATE TABLE standard_users(
	user_id SERIAL REFERENCES logins(user_id) PRIMARY KEY,
	inventory VARCHAR(800),
	decks VARCHAR(100)
);

CREATE TABLE decks(
	deck_id SERIAL PRIMARY KEY,
	user_id SERIAL REFERENCES standard_users(user_id),
	deck_name VARCHAR(50),
	card_list VARCHAR(500),
	card_count INTEGER
);

CREATE OR REPLACE FUNCTION populate_standard_users() RETURNS TRIGGER AS 
$BODY$
BEGIN 
	IF NEW.acc_type = 'standard' OR NEW.acc_type = 'moderator' THEN
		INSERT INTO standard_users (user_id, inventory, decks) 
		VALUES(NEW.user_id, NULL, NULL); 
	END IF;
	RETURN NEW;
END
$BODY$
LANGUAGE plpgsql;
	
CREATE OR REPLACE FUNCTION remove_standard_user() RETURNS TRIGGER AS 
$BODY$
BEGIN 
	DELETE FROM decks WHERE user_id = OLD.user_id;
	DELETE FROM standard_users WHERE user_id = OLD.user_id;
	RETURN NEW;
END
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER remove_standard_user AFTER DELETE ON logins
		FOR EACH ROW 
		EXECUTE PROCEDURE remove_standard_user();
	
CREATE TRIGGER populate_standard_users AFTER INSERT ON logins
		FOR EACH ROW 
		EXECUTE PROCEDURE populate_standard_users();
		
TRUNCATE TABLE standard_users RESTART IDENTITY CASCADE;
DROP TABLE IF EXISTS decks CASCADE;