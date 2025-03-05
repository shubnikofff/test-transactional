drop table if exists author cascade;
create table author
(
    name  varchar primary key,
    likes integer not null default 0
);

drop table if exists history;
create table history
(
    id          serial primary key,
    user_name   varchar   not null,
    author_name varchar   not null,
    like_amount integer   not null,
    created_at  timestamp not null,
    saved_at    timestamp not null default now(),

    constraint fk_author foreign key (author_name) references author (name) on delete cascade
);

drop view if exists total_count;
create view total_count as
select a.name, a.likes as author_likes, sum(h.like_amount) as history_likes
from history h
         inner join author a on h.author_name = a.name
group by a.name;
