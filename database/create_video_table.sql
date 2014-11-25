drop table if exists video cascade;
drop table if exists person cascade;
drop table if exists actor cascade;
drop table if exists director cascade;
drop table if exists producer cascade;
drop type if exists gender;

--type 1 for movies, 2 for tv shows, 3 for others
create table video
    (
    videoId serial,
    title varchar(200) not null,
    runtime integer,
    type integer not null default 1,
    poster varchar(200),
    genre text,
    relatedLinks text[],
    language varchar(200) default 'English',
    countryCode varchar(200),
    synopsis varchar(2000),
    userRating float default 0.0,
    totalRate integer default 0,
    certification varchar(500),
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

insert into video (title, runtime, type, genre, language, countryCode, synopsis, certification, poster) values
( 'The Shawshank Redemption' , '142 ' , '1' , 'Crime' , 'English' , 'USA' , 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.' , 'Rated R for language and prison violence', '/images/movies/posters/1.jpg'),
( '12 Angry Men' , '96 ' , '1' , 'Drama' , 'English' , 'USA' , 'A dissenting juror in a murder trial slowly manages to convince the others that the case is not as obviously clear as it seemed in court.' , '', '/images/movies/posters/2.jpg'),
( 'The Dark Knight' , '152 ' , '1' , 'Action' , 'English' , 'USA' , 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.' , '','/images/movies/posters/3.jpg'),
( 'The Godfather: Part II' , '200 ' , '1' , 'Crime' , 'English' , 'USA' , 'The early life and career of Vito Corleone in 1920s New York is portrayed while his son, Michael, expands and tightens his grip on his crime syndicate stretching from Lake Tahoe, Nevada to pre-revolution 1958 Cuba.' , 'R', '/images/movies/posters/4.jpg'),
( 'Schindlers List' , '195 ' , '1' , 'Biography' , 'English' , 'Official Facebook' , 'In Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.' , '', '/images/movies/posters/5.jpg'),
( 'The Good, the Bad and the Ugly' , '161 ' , '1' , 'Western' , 'Spain' , 'Italy' , 'A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.' , '', '/images/movies/posters/6.jpg'),
( 'Pulp Fiction' , '154 ' , '1' , 'Crime' , 'English' , 'USA' , 'The lives of two mob hit men, a boxer, a gangsters wife, and a pair of diner bandits intertwine in four tales of violence and redemption.' , '', '/images/movies/posters/7.jpg'),
( 'The Godfather' , '175 ' , '1' , 'Crime' , 'English' , 'English' , 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.' , 'R', '/images/movies/posters/8.jpg'),
( 'The Lord of the Rings: The Return of the King' , ' 201 ' , '1' , 'Adventure' , 'English' , 'USA' , 'Gandalf and Aragorn lead the World of Men against Saurons army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.' , '','/images/movies/posters/9.jpg'),
( 'The Lord of the Rings: The Fellowship of the Ring' , '178 ' , '1' , 'Adventure' , 'Allocine (French)' , 'USA' , 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.' , '','/images/movies/posters/10.jpg');

insert into person (personName , personGender) values
( 'Tim Robbins' , 'male'),
( 'Frank Darabont' , 'male'),
( 'Castle Rock Entertainment' , 'other'),
( 'Henry Fonda' , 'male'),
( 'Sidney Lumet' , 'male'),
( 'Orion-Nova Productions' , 'other'),
( 'Christian Bale' , 'male'),
( 'Christopher Nolan' , 'male'),
( 'Warner Bros.' , 'other'),
( 'Al Pacino' , 'male'),
( 'Francis Ford Coppola' , 'male'),
( 'Paramount Pictures' , 'other'),
( 'Liam Neeson' , 'male'),
( 'Steven Spielberg' , 'male'),
( 'Universal Pictures' , 'other'),
( 'Clint Eastwood' , 'male'),
( 'Sergio Leone' , 'male'),
( 'Produzioni Europee Associati (PEA)' , 'other'),
( 'John Travolta' , 'male'),
( 'Quentin Tarantino' , 'male'),
( 'Miramax Films' , 'other'),
( 'Marlon Brando' , 'male'),
( 'Francis Ford Coppola' , 'male'),
( 'Paramount Pictures' , 'other'),
( 'Elijah Wood' , 'male'),
( 'Peter Jackson' , 'male'),
( 'New Line Cinema' , 'other'),
( 'Elijah Wood' , 'male'),
( 'Peter Jackson' , 'male'),
( 'New Line Cinema' , 'other');

insert into actor (videoId , personId, roleName) values
( '1' , '1' , '{Andy Duferen}'),
( '2' , '2' , '{Juror #8}'),
( '3' , '3' , '{Bruce Wayne, Batman}'),
( '4' , '4' , '{Michael}'),
( '5' , '5' , '{Oskar Schindler}'),
( '6' , '6' , '{Blondie} '),
( '7' , '7' , '{Vincent Vega}'),
( '8' , '8' , '{Don Vito Corleone}'),
( '9' , '9' , '{Frodo}'),
( '10' , '10' , '{Frodo Baggins}');

insert into producer (videoId , personId) values
( '1' , '1'),
( '2' , '2'),
( '3' , '3'),( '4' , '4'),( '5' , '5'),( '6' , '6'),( '7' , '7'),( '8' , '8'),( '9' , '9'),( '10' , '10');

insert into director (videoId , personId) values
( '1' , '1'),
( '2' , '2'),
( '3' , '3'),
( '4' , '4'),
( '5' , '5'),
( '6' , '6'),
( '7' , '7'),
( '8' , '8'),
( '9' , '9'),( '10' , '10');
