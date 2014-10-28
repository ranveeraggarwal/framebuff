drop table if exists video cascade;
drop table if exists person cascade;
drop table if exists actor cascade;
drop table if exists director cascade;
drop table if exists producer cascade;
drop table if exists fact cascade;
drop table if exists trivia cascade;
drop type if exists gender;


create table video
    (
    videoId serial,
    title varchar(50) not null,
    runtime integer,
    type integer not null default 1,
    genre text[],
    relatedLinks text[],
    language varchar(50),
    countryCode varchar(20),
    synopsis varchar(50),
    userRating float,
    totalRate integer,
    certification varchar(50),
    primary key(videoId)
    );

create type gender as enum ('male', 'female', 'other');
create table person
(
    personId serial primary key,
    personName varchar(100),
    personGender gender
);

create table actor 
(
    actorId serial primary key,
    videoId integer references video (videoId) on delete cascade,
    personId integer references person (personId) on delete cascade,
    roleName text[]
);

create table fact
(
    factId serial primary key,
    videoId integer references video(videoId) on delete cascade,
    factText text,
    upvotes integer,
    downvotes integer
);

create table trivia
(
    triviaId serial primary key,
    videoId integer references video (videoId) on delete cascade,
    triviaText text,
    upvotes integer,
    downvotes integer
);

create table director
(
    directorId serial primary key,
    personId integer references person (personId) on delete cascade,
    videoId integer references video (videoId) on delete cascade
);

create table producer
(
    producerId serial primary key,
    personId integer references person (personId) on delete cascade,
    videoId integer references video (videoId) on delete cascade
);

insert into video (title, runtime) values
('Dark Knight Rises', '170'),
('Shawshank Redemption', '180'),
('Ramgopal verma ki aag', '180'),
('Intersteller', '190'),
('Chutiyapa', '150');