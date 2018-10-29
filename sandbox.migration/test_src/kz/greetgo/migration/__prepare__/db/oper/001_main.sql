create type addr_type as enum ('FACT','REG');;

create table client_addr
        (
            id serial PRIMARY KEY NOT NULL,
            client_id bigserial,
            type addr_type NOT NULL,
            street varchar(255),
            house varchar(255),
            flat varchar(255),
            actual boolean default false
);;

create table client (
  id serial not null primary key,

  surname varchar(300) not null,
  name varchar(300) not null,
  patronymic varchar(300),
  birth_date date not null,
  charm varchar not null,


  actual smallint not null default 0,
  cia_id varchar(100)
);;

create sequence s_client start with 1000000;;
