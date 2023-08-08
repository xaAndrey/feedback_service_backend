CREATE TABLE IF NOT EXISTS public_user (
	user_id int4 NOT NULL GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE),
	"password" varchar(256) NOT NULL,
	username varchar(256) NOT NULL,
	security_stamp varchar(256) NOT NULL,
	registration_date timestamptz NOT NULL,
	account_locked bool NOT NULL,
	CONSTRAINT public_user_pkey PRIMARY KEY (user_id),
	CONSTRAINT unique_user_username UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS registration (
	registration_id int4 NOT NULL GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE),
	fio_person varchar(256) NOT NULL,
	phone_number varchar(256) NOT NULL,
	doctor_spec varchar(256) NOT NULL,
	date_registration timestamptz NOT NULL,
	is_registered bool NOT NULL,
	comments_reg varchar(256) NULL,
	CONSTRAINT registration_pkey PRIMARY KEY (registration_id)
);