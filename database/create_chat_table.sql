drop table if exists chat cascade;

create table chat
(
    chatId serial primary key,
    videoId integer references video (videoId) on delete cascade,
    userId integer references users (userId) on delete cascade,
    message text,
    parentId integer default -1,
    chatDate timestamp default now()
    
);

alter table chat add constraint pidfk foreign key (parentId) references chat (chatId) on delete cascade;

insert into chat values ('-1', null, null, null, null, null);