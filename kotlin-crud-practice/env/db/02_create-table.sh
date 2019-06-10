#!/bin/bash

psql -U postgres -d kotlin-crud << "EOSQL"

CREATE TABLE users (
    user_id bigint,
    last_name varchar(40),
    first_name varchar(40),
    birth_day date,
    PRIMARY KEY(user_id)
);

CREATE SEQUENCE user_id_seq;

ALTER TABLE users OWNER TO "crud-user";
ALTER SEQUENCE user_id_seq OWNER TO "crud-user";


EOSQL