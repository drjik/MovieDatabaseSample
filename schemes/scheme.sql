create table genres
(
    id   serial8,
    name varchar not null,

    primary key (id)
);

create table directors
(
    id       serial8,
    name     varchar not null,
    lastname varchar not null,

    primary key (id)
);

create table actors
(
    id       serial8,
    name     varchar not null,
    lastname varchar not null,

    primary key (id)
);

create table movies
(
    id          serial8,
    name        varchar       not null,
    year        int4          not null,
    genres_id   int8          not null,
    director_id int8          not null,
    rating      numeric(4, 2) not null,

    primary key (id),
    foreign key (genres_id) references genres (id),
    foreign key (director_id) references directors (id)
);

create table movies_directors
(
    id          serial8,
    movie_id    int8 not null,
    director_id int8 not null,

    primary key (id),
    foreign key (movie_id) references movies (id),
    foreign key (director_id) references actors (id)
);

create table movies_actors
(
    id       serial8,
    movie_id int8 not null,
    actor_id int8 not null,

    primary key (id),
    foreign key (movie_id) references movies (id),
    foreign key (actor_id) references actors (id)
);

create table users
(
    id       serial primary key,
    login    text,
    password text
);

create table roles
(
    id   serial primary key,
    name text
);

create table user_roles
(
    id      serial primary key,
    user_id integer references users (id),
    role_id integer references roles (id)
);

insert into users(login, password)
values ('admin', '$2a$12$tmi1y6Up4jztiuJBKdLzlOZFO45uS9yDZjTWm02x3YSXhlI.cS4Y6'),
       ('user', '$2a$12$tmi1y6Up4jztiuJBKdLzlOZFO45uS9yDZjTWm02x3YSXhlI.cS4Y6');

insert into roles(name)
values ('admin'), ('user');

insert into user_roles (user_id, role_id)
values ((select id from users where login = 'admin'), (select id from roles where name = 'admin')),
       ((select id from users where login = 'user'), (select id from roles where name = 'user'));

SELECT *
FROM movies
         LEFT JOIN movies_actors ON movies.id = movies_actors.movie_id
         LEFT JOIN actors ON movies_actors.actor_id = actors.id
         LEFT JOIN movies_directors ON movies.id = movies_directors.movie_id
         LEFT JOIN directors ON movies_directors.director_id = directors.id
WHERE
    movies.name = :name
  AND (movies.year BETWEEN :yearFrom AND :yearTo OR :yearFrom IS NULL OR :yearTo IS NULL)
  AND (movies.rating = :rating OR :rating IS NULL)
  AND (actors.name = :actor OR :actor IS NULL)
  AND (directors.name = :director OR :director IS NULL);