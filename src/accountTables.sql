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

DELETE FROM logins;
DELETE FROM standard_users;
