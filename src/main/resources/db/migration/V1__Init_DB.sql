create sequence hibernate_sequence start 1 increment 1;

create table paste (
id integer not null auto_increment,
author varchar(255),
expire_date timestamp,
hash varchar(255),
text varchar(255),
type integer,
primary key (id));