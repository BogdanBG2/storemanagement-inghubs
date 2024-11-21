create table if not exists users (
    id bigint primary key,
    name varchar(255),
    username varchar(255) not null unique,
    password varchar(255),
    role varchar(255)
);

create table if not exists products (
    id bigint primary key,
    name varchar(255) not null,
    description varchar(255),
    price double precision,
    category varchar(255)
);