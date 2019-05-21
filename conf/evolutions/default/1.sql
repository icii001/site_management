# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cost (
  id                        bigint auto_increment not null,
  site_id                   bigint,
  c_unit_price              decimal(38),
  c_quantity                decimal(38),
  constraint pk_cost primary key (id))
;

create table site (
  id                        bigint auto_increment not null,
  site_name                 varchar(255),
  address                   varchar(255),
  tel                       varchar(255),
  remarks                   varchar(255),
  constraint pk_site primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table cost;

drop table site;

SET FOREIGN_KEY_CHECKS=1;

