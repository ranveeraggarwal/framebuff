drop table if exists userVideo cascade;
drop table if exists factVote cascade;
drop table if exists triviaVote cascade;

create table userVideo
(
    userVideoId serial primary key,
    userId integer references users (userId) on delete cascade,
    videoId integer references video (videoId) on delete cascade,
    rating integer,
    review text,
    watchDate timestamp
    
);

create table factVote
(
    factVoteId serial primary key,
    userId integer references users (userId) on delete cascade,
    factId integer references fact (factId) on delete cascade,
    vote integer
);

create table triviaVote
(
    triviaVoteId serial primary key,
    userId integer references users (userId) on delete cascade,
    triviaId integer references trivia (triviaId) on delete cascade,
    vote integer
);

insert into userVideo (userId, videoId, rating, review, watchDate) values
('1', '1', '10', 'This is an awesome and watchable movie. Everyone gonna love this movie from Christoper ''Batman'' Nolan', now()),
('1', '2', '10', 'Probably the best movie I have seen', now()),
('2', '1', '10', 'This is epic', now());