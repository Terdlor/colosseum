drop table if exists heroes;
drop table if exists users;


create table users
(
    id              serial       not null,
    uuid            uuid         not null default gen_random_uuid() primary key,
    telegram_id     numeric      not null,
    telegram_handle varchar(255),
    is_bot          boolean      not null,
    first_name      varchar(255),
    last_name       varchar(255),
    insert_date     timestamp(6)          default now(),
    battle_state    varchar(100) not null default 'FREE'
);
create unique index users_id_uindex on users (id);
create unique index users_uuid_uindex on users (uuid);


create table heroes
(
    id           serial       not null,
    uuid         uuid         not null default gen_random_uuid() primary key,
    user_uuid    uuid         not null,
    is_alive     boolean      not null default true,
    name         varchar(255) not null,
    class        varchar(255) not null,
    level        numeric(2)   not null,
    experience   numeric      not null,
    strength     numeric(5)   not null,
    dexterity    numeric(5)   not null,
    intelligence numeric(5)   not null,
    luck         numeric(5)   not null,
    health_max   numeric(5)   not null,
    health       numeric(5)   not null,
    defence      numeric(5)   not null,
    damage_min   numeric(5)   not null,
    damage_max   numeric(5)   not null,
    dodge        numeric(5)   not null,
    crit_chance  numeric(5)   not null,
    battles_won  numeric(5)   not null default 0,
    constraint fk_user_uuid FOREIGN KEY (user_uuid) references users (uuid)
);
create unique index heroes_id_uindex on heroes (id);
create unique index heroes_uuid_uindex on heroes (uuid);

select * from users