CREATE USER "crud-user" WITH PASSWORD 'crud-user';
CREATE DATABASE "kotlin-crud" WITH OWNER = "crud-user" ENCODING = "UTF-8";
GRANT ALL PRIVILEGES ON DATABASE "kotlin-crud" TO "crud-user";