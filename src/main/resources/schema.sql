drop table if exists author;

create table author
(
    name  varchar primary key,
    likes integer not null default 0
);
