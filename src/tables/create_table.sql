
create table user
	(id integer,
	firstName varchar(50) not NUll,
	lastName varchar(50),
	userName varchar(50) not Null,
	countryCode varchar(20), #doubt
	language varchar(50), ## enum can be used?
	phone varchar(12),
	primary key (id)
	);

create table video
	(title varchar(50),
	 runtime integer,
	type integer,
	cast array,
	directors array,
	producers array,
	genre array,
	facts array,
	trivia array,
	parodies array,
	relatedLinks array,
	language varchar(50),
	countryCode varchar(20),
	synopsis varchar(50),
	user_rating float,
	certification varchar(50),
	primary key(title)
	);
