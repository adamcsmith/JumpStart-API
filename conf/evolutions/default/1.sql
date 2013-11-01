# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table APP_USER (
  id                        bigint not null,
  created                   timestamp,
  updated                   timestamp,
  username                  varchar(255),
  password                  varchar(255),
  temporary_password        varchar(255),
  temporary_password_expiration timestamp,
  salt                      varchar(255),
  last_login                timestamp,
  failed_login_attempts     integer,
  constraint pk_APP_USER primary key (id))
;

create sequence APP_USER_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists APP_USER;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists APP_USER_seq;

