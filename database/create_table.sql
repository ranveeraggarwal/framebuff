DROP table if exists users cascade;
DROP table if exists video cascade;
DROP table if exists auth cascade;
DROP table if exists chat cascade;
DROP table if exists chatroom cascade;

create table users
	(
	userId serial,
	firstName varchar(50) not NUll,
	lastName varchar(50),
	username varchar(50) not Null unique,
	email varchar(50) not null unique,
	countryCode varchar(20),
	language varchar(50), 
	phone varchar(12),
	primary key (userId)
	);

create table video
	(
	videoId serial,
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
	userRating float,
	certification varchar(50),
	primary key(videoId)
	);
	
create table auth
    (
    userId integer REFERENCES users (userId) on delete cascade primary key,
    password varchar(128)
    );
    
create table chatroom
(
    chatroomId serial primary key,
    videoId integer references video(videoId) on delete cascade,
    name varchar(50)
);

create table chat
(
    chatId serial primary key,
    userId integer references users (userId) on delete cascade,
    nick varchar(50),
    message text,
    chatroom integer references chatroom (chatroomId) on delete cascade,
    parentId integer default -1,
    chatDate date
    
);

alter table chat add constraint pidfk foreign key (parentId) references chat (chatId) on delete cascade;

insert into users (firstName, lastName, username, email, countryCode, language, phone) values
    ('Dheerendra', 'Rathor', 'DheerendraRathor', 'dheeru.rathor14@gmail.com', 'IN', 'English', 8879538411),
    ('Ranveer', 'Aggarwal', 'ranveeraggarwal', 'ranveer@gmail.com', 'IN', 'English', 239423024),
    ('Nitin', 'Chandrol', 'nitin', 'ntnhacker@gmail.com', 'IN', 'English', 2938420123);
    
insert into auth (userId, password) values
    ('1', 'dheerendra'),
    ('2', 'ranveer'),
    ('3', 'rashmi');

insert into chat values ('-1', null, null, null, null, null, null);