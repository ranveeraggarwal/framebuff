--#command :$psql -h localhost -d framebuff_db -U framebuff_user < database/create_table.sql
DROP table if exists users cascade;
DROP table if exists auth cascade;

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
	
create table auth
    (
    userId integer REFERENCES users (userId) on delete cascade primary key,
    password varchar(128)
    );


insert into users (firstName, lastName, username, email, countryCode, language, phone) values
    ('Dheerendra', 'Rathor', 'DheerendraRathor', 'dheeru.rathor14@gmail.com', 'IN', 'English', 8879538411),
    ('Ranveer', 'Aggarwal', 'ranveeraggarwal', 'ranveer@gmail.com', 'IN', 'English', 239423024),
    ('Nitin', 'Chandrol', 'nitin', 'ntnhacker@gmail.com', 'IN', 'English', 2938420123);
    
insert into auth (userId, password) values
    ('1', 'dheerendra'),
    ('2', 'ranveer'),
    ('3', 'rashmi');
