drop table if exists heroes;
drop table if exists users;
drop table if exists battles;

create table users
(
    id              bigserial      not null primary key,
    telegram_id     numeric unique not null,
    telegram_handle varchar(255),
    is_bot          boolean        not null,
    first_name      varchar(255),
    last_name       varchar(255),
    date_created    timestamp(6) default now()
);
create unique index users_id_uindex on users (id);

create table heroes
(
    id              bigserial    not null primary key,
    user_id         integer      not null,
    is_alive        boolean      not null default true,
    name            varchar(255) not null,
    class_type      varchar(255) not null,
    level           numeric(2)   not null,
    experience      numeric      not null,
    strength        numeric(5)   not null,
    dexterity       numeric(5)   not null,
    intelligence    numeric(5)   not null,
    luck            numeric(5)   not null,
    health_max      numeric(5)   not null,
    health          numeric(5)   not null,
    defence         numeric(5)   not null,
    damage_min      numeric(5)   not null,
    damage_max      numeric(5)   not null,
    dodge           numeric(5)   not null,
    critical_chance numeric(5)   not null,
    battles_won     numeric(5)   not null default 0,
    constraint fk_user_id foreign key (user_id) references users (id)
);
create unique index heroes_id_uindex on heroes (id);

create table battles
(
    id            bigserial    not null primary key,
    caller_id     integer      not null,
    responder_id  integer      not null,
    date_started  timestamp(6) default now(),
    date_finished timestamp(6),
    state         varchar(255) not null,
    result        varchar,
    constraint fk_caller_id foreign key (caller_id) references users (id),
    constraint fk_responder_id foreign key (responder_id) references users (id)
);
create unique index battles_id_uindex on battles (id);