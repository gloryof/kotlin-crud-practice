FROM postgres:11

COPY db/01_create-database.sql /docker-entrypoint-initdb.d/01_create-database.sql
COPY db/02_create-table.sh /docker-entrypoint-initdb.d/02_create-table.sh