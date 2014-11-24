drop function if exists update_rating() cascade;
drop trigger if exists update_rating on uservideo cascade;
drop table if exists userVideo cascade;
drop table if exists factVote cascade;
drop table if exists triviaVote cascade;
drop type if exists watchType;

create type watchType as enum ('watched', 'want', 'watching');
create table userVideo
(
    userVideoId serial primary key,
    userId integer references users (userId) on delete cascade,
    videoId integer references video (videoId) on delete cascade,
    watch watchType,
    rating integer default 0,
    rated boolean default 'f',
    review text,
    watchDate timestamp default now()
    
);

create or replace function update_rating() returns trigger as $update_rating$
BEGIN 
    if (OLD.rated = 'f' AND NEW.rated = 't') then
        update video set totalrate = totalrate + 1, 
            userRating = ((userRating * totalrate) + NEW.rating)/(totalrate + 1)
        where videoId = NEW.videoId;
    ELSIF (NEW.rated = 't' AND OLD.rated = 't') THEN
        UPDATE video SET userRating = ((userRating * totalRate) - OLD.rating + NEW.rating)/totalRate
        WHERE videoId = NEW.videoId;
    END IF;
    RETURN NEW;
END;
$update_rating$ language plpgsql;

create trigger update_rating before update on userVideo
for each row execute procedure update_rating();

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

insert into userVideo (userId, videoId, watch, review, watchDate) values
('1', '1', 'watched', 'This is an awesome and watchable movie. Everyone gonna love this movie from Christoper ''Batman'' Nolan', now()),
('1', '2', 'watched', 'Probably the best movie I have seen', now()),
('2', '1', 'want', 'This is epic', now());