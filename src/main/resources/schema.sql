use mybatis;
create table users
(
    id           bigint      not null auto_increment,
    login_id     varchar(20) not null unique,
    login_pw     varchar(20) not null,
    name         varchar(20) not null,
    phone_number varchar(20) not null unique,
    primary key (id)
);
# test data
INSERT INTO users(login_id, login_pw, name, phone_number)
VALUES ('1', '1', '1', '1');