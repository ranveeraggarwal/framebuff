--#command :$psql -h localhost -d framebuff_db -U framebuff_user < database/create_table.sql
DROP table if exists users cascade;
DROP table if exists auth cascade;
DROP table if exists follow cascade;

create table users
	(
	userId serial,
	firstName varchar(50),
	lastName varchar(50),
	username varchar(50) unique,
	email varchar(50) not null unique,
	countryCode varchar(20),
	language varchar(50), 
	phone varchar(12),
	aboutme text,
	primary key (userId)
	);
	
create table auth
    (
    userId integer REFERENCES users (userId) on delete cascade primary key,
    password varchar(128)
    );
    
create table follow
(
    followId serial primary key,
    followee integer references users (userId) on delete cascade,
    follower integer references users (userId) on delete cascade
);


insert into users (firstName, lastName, username, email, countryCode, language, phone) values
    ('Dheerendra', 'Rathor', 'DheerendraRathor', 'dheeru.rathor14@gmail.com', 'IN', 'English', 8879538411),
    ('Ranveer', 'Aggarwal', 'ranveeraggarwal', 'ranveer@gmail.com', 'IN', 'English', 239423024),
    ('Nitin', 'Chandrol', 'nitin', 'ntnchandrol@gmail.com', 'IN', 'English', 2938420123),
    ('Shubham', 'Jain', 'dora', 'dora@gmail.com', 'IN', 'English', 2938420123),
    ('Suman', 'Sourabh', 'suman', 'suman@gmail.com', 'IN', 'English', 2938420123),
    ('Rakesh', 'Ranjan', 'rakesh', 'rakesh@gmail.com', 'IN', 'English', 2938420123),
    ('Aman', 'Gour', 'aman', 'aman@gmail.com', 'IN', 'English', 2938420123),
    ('Dinesh', 'Kota', 'dinu', 'dinesh@gmail.com', 'IN', 'English', 2938420123),
    ('Mahinder', 'Kota', 'mahi', 'mahi@gmail.com', 'IN', 'English', 2938420123),
    ('Rohit', 'Kumar', 'rohit', 'rohit@gmail.com', 'IN', 'English', 2938420123),
    ('Manohar', 'Kumar', 'mannu', 'mannu@gmail.com', 'IN', 'English', 2938420123),
    ('Pranay', 'Dhondi', 'pranay', 'pranay@gmail.com', 'IN', 'English', 2938420123),
    ('Akhil', 'Mududpu', 'akhil', 'akhil@gmail.com', 'IN', 'English', 2938420123),
    ('Jassem', 'Umar', 'jaseem', 'jaseem@gmail.com', 'IN', 'English', 2938420123);
    
    
insert into auth (userId, password) values
    ('1', md5('dheerendra')),
    ('2', md5('ranveer')),
    ('3', md5('nitin')),
    ('4', md5('dora')),
    ('5', md5('suman')),
    ('6', md5('rakesh')),
    ('7', md5('aman')),
    ('8', md5('dinu')),
    ('9', md5('mahi')),
    ('10', md5('rohit')),
    ('11', md5('mannu')),
    ('12', md5('pranay')),
    ('13', md5('akhil')),
    ('14', md5('jaseem'))
    ;

insert into follow (followee, follower) values 
    ('1', '2'), ('1', '3'), ('2', '1'), ('2', '3'), ('3', '1'), ('3', '2'),
    ('5', '4'), ('1', '5'), ('2', '4'), ('2', '13'), ('13', '1'), ('3', '12'),
    ('1', '8'), ('1', '7'), ('2', '9'), ('2', '5'), ('3', '11'), ('3', '10')
    ;
