DROP table users cascade;
DROP table video cascade;
DROP table auth cascade;

create table users
	(
	id serial,
	firstName varchar(50) not NUll,
	lastName varchar(50),
	username varchar(50) not Null unique,
	email varchar(50) not null unique,
	countryCode varchar(20),
	language varchar(50), 
	phone varchar(12),
	primary key (id)
	);

create table video
	(
	id serial,
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

insert into users (firstName, lastName, username, email, countryCode, language, phone) values
    ('Dheerendra', 'Rathor', 'DheerendraRathor', 'dheeru.rathor14@gmail.com', 'IN', 'English', 8879538411),
    ('Ranveer', 'Aggarwal', 'ranveeraggarwal', 'ranveer@gmail.com', 'IN', 'English', 239423024),
    ('Nitin', 'Chandrol', 'nitin', 'ntnhacker@gmail.com', 'IN', 'English', 2938420123);
    
insert into auth (id, password) values
    ('1', 'dheerendra'),
    ('2', 'ranveer'),
    ('3', 'rashmi');