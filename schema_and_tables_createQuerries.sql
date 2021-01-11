CREATE SCHEMA "bankApp" AUTHORIZATION postgres;



CREATE TABLE "bankApp"."user" (
	user_id serial NOT null primary key,
	user_name text NOT null unique,
	"password" text NOT NULL,
	first_name text NOT NULL,
	last_name text NOT NULL,
	is_employee bool NOT NULL,
	employment_status text NOT NULL,
	dob date NOT NULL
);

CREATE TABLE "bankApp".account (
	account_id serial NOT null primary key,
	user_id int8 NOT null REFERENCES "bankApp".user(user_id),
	balance numeric(10,2) NOT NULL,
	date_created date NOT NULL
);



CREATE TABLE "bankApp".transactions (
	transaction_id serial NOT null primary key,
	from_account_id int8 NOT NULL references account(account_id),
	to_account_id int8 NOT NULL references account(account_id),
	amount numeric(10,2) NOT NULL,
	status text NOT NULL,
	tranfer_date date NOT NULL
);




CREATE TABLE "bankApp".account_request (
	request_id serial NOT null primary key,
	user_id int8 NOT null references "bankApp".user(user_id),
	request_status text NOT NULL,
	rejection_reason text,
	date_requested date NOT NULL
	starting_balance numeric(10,2) NOT NULL
);

 ALTER SEQUENCE user_user_id_seq RESTART WITH 111111;
 ALTER SEQUENCE account_account_id_seq RESTART WITH 21000;
 ALTER SEQUENCE account_request_request_id_seq RESTART WITH 550000;
 ALTER SEQUENCE transactions_transaction_id_seq RESTART WITH 6660000;
