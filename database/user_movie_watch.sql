drop table if exists userVideo cascade;
drop table if exists factVote cascade;
drop table if exists triviaVote cascade;

create table userVideo
(
    userVideoId serial,
    userId integer references users (userId) on delete cascade,
    videoId integer references video (videoId) on delete cascade,
    rating integer
);

create table factVote
(
    factVoteId serial,
    userId integer references users (userId) on delete cascade,
    factId integer references fact (factId) on delete cascade,
    vote integer
);

create table triviaVote
(
    triviaVoteId serial,
    userId integer references users (userId) on delete cascade,
    triviaId integer references trivia (tirivaId) on delete cascade,
    vote integer
);