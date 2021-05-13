drop table if exists user;
drop table if exists link;
drop table if exists comment;
drop table if exists notify;
drop table if exists profile;
drop table if exists user_like;

create table user (
    id bigint auto_increment primary key ,
    username varchar(255) not null unique ,
    email varchar(255) not null unique ,
    password varchar(511) not null
) engine = InnoDB default charset = utf8mb4;

create table link (
    id bigint auto_increment primary key ,
    user_id bigint not null ,
    title varchar(511) not null ,
    url varchar(511) not null ,
    create_time bigint not null
) engine = InnoDB default charset = utf8mb4;

create table comment (
    id bigint auto_increment primary key ,
    user_id bigint not null ,
    link_id bigint not null ,
    content varchar(2047),
    create_time bigint not null
) engine = InnoDB default charset = utf8mb4;

create table notify (
    id bigint auto_increment primary key ,
    user_id bigint not null ,
    at_user_id bigint not null ,
    link_id bigint not null ,
    comment_id bigint not null ,
    create_time bigint not null ,
    notify_type int not null ,
    is_unread int not null
) engine = InnoDB default  charset = utf8mb4;

create table profile (
    id bigint primary key auto_increment,
    user_id bigint not null ,
    sex int ,
    bio varchar(255)
) engine = InnoDB default charset = utf8mb4;

create table user_like (
    id bigint auto_increment primary key ,
    link_id bigint not null ,
    user_id bigint not null ,
    is_like int not null
) engine = InnoDB default charset = utf8mb4;
alter table user_like add unique index link_user (link_id, user_id);

create table link_total_like (
    id bigint auto_increment primary key ,
    link_id bigint not null unique ,
    like_count bigint default 0
) engine = InnoDB default charset = utf8mb4;

create table feed_folder (
                             id bigint auto_increment primary key ,
                             user_id bigint not null ,
                             folder_name varchar(255) not null
) engine = InnoDB, charset = utf8mb4;

create table feed (
                      id bigint auto_increment primary key ,
                      user_id bigint not null ,
                      folder_id bigint not null ,
                      feed_url varchar(255) not null
) engine = InnoDB, charset = utf8mb4;

create table feed_content (
                              id bigint auto_increment primary key ,
                              feed_id bigint not null ,
                              title varchar(255) not null ,
                              description text not null ,
                              publish_time bigint not null,
                              link varchar(255)
) engine = InnoDB, charset = utf8mb4;

create table user_read (
                           id bigint auto_increment primary key ,
                           feed_id  bigint not null ,
                           content_id  bigint not null ,
                           user_id bigint not null ,
                           is_read int not null default 0
) engine = InnoDB, charset = utf8mb4;