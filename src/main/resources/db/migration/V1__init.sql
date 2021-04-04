create table overlords
(
    id          bigserial primary key,
    name        varchar(255) not null,
    age         bigint not null
);

insert into overlords (name, age)
values ('Jack', 30000000),
       ('Julian', 10000000912),
       ('Artem', 123812301283132),
       ('Egor', 11031231312);

create table planets
(
    id          bigserial primary key,
    name        varchar(255) not null,
    owner_id    bigint,
    FOREIGN KEY (owner_id) REFERENCES overlords (id)
);

insert into planets (name, owner_id)
values ('Earth', 3),
       ('Mars', 1),
       ('Reptilian', 1),
       ('Russia', 1),
       ('Venus', 2),
       ('W1-RG-52', 1);