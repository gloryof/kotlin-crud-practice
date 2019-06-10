#!/bin/bash

psql -U postgres -d kotlin-crud << "EOSQL"

CREATE TABLE users (
    id bigint,
    name varchar(40),
    birthDay date,
    PRIMARY KEY(id)
);

CREATE SEQUENCE user_id_seq;

ALTER TABLE users OWNER TO "crud-user";
ALTER SEQUENCE user_id_seq OWNER TO "crud-user";


EOSQL