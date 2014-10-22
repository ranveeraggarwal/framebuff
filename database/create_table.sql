
create table users
	(id integer,
	firstName varchar(50) not NUll,
	lastName varchar(50),
	userName varchar(50) not Null,
	countryCode varchar(20),
	language varchar(50), 
	phone varchar(12),
	primary key (id)
	);

create table video
	(
	id integer,
	title varchar(50) not null,
	runtime integer,
	type integer not null default 1,
	actors text[],
	directors text[],
	producers text[],
	genre text[],
	facts text[],
	trivia text[],
	parodies text[],
	relatedLinks text[],
	language varchar(50),
	countryCode varchar(20),
	synopsis varchar(50),
	user_rating float,
	certification varchar(50),
	primary key(id)
	);
	
create table auth
    (
    id integer REFERENCES users (id),
    password varchar(128)
    );
