# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table APP_USER (
  id                        varchar(255) not null,
  created                   datetime,
  updated                   datetime,
  username                  varchar(255),
  password                  varchar(255),
  temporary_password        varchar(255),
  temporary_password_expiration datetime,
  salt                      varchar(255),
  last_login                datetime,
  failed_login_attempts     integer,
  constraint pk_APP_USER primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table if exists APP_USER;

SET FOREIGN_KEY_CHECKS=1;

