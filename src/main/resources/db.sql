create database navigator;

use navigator;

create table if not exists navigator.cities
(
    id           int auto_increment primary key,
    name         varchar(50)   not null,
    x_coordinate decimal(5, 1) null,
    y_coordinate decimal(5, 1) null
);

create table navigator.paths
(
    id         int auto_increment
        primary key,
    start_city int           not null,
    end_city   int           not null,
    distance   decimal(5, 1) null,
    constraint start_city
        unique (start_city, end_city),
    constraint paths_ibfk_1
        foreign key (start_city) references navigator.cities (id),
    constraint paths_ibfk_2
        foreign key (end_city) references navigator.cities (id)
);

create table navigator.shortestpaths
(
    id               int auto_increment
        primary key,
    origin_city      int           not null,
    destination_city int           not null,
    total_distance   decimal(5, 1) null,
    path_sequence    json          not null,
    constraint origin_city
        unique (origin_city, destination_city),
    constraint shortestpaths_ibfk_1
        foreign key (origin_city) references navigator.cities (id),
    constraint shortestpaths_ibfk_2
        foreign key (destination_city) references navigator.cities (id)
);
