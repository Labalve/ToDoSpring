DROP DATABASE IF EXISTS ToDo;
CREATE DATABASE ToDo;
USE ToDo;
CREATE TABLE users (
    uuid VARCHAR(50) PRIMARY KEY,
    api_key VARCHAR(30),
    role VARCHAR(20));
CREATE TABLE projects (
    uuid VARCHAR(50) PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(120),
    date_due DATETIME,
    outcome VARCHAR(30),
    author_id VARCHAR(50),
    FOREIGN KEY (author_id) REFERENCES users(uuid));
CREATE TABLE tasks (
    uuid VARCHAR(50) PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(120),
    project_id VARCHAR(50),
    date_due DATETIME,
    outcome VARCHAR(30),
    author_id VARCHAR(50),
    FOREIGN KEY (author_id) REFERENCES users(uuid),
    FOREIGN KEY (project_id) REFERENCES projects(uuid));
INSERT INTO users (uuid, api_key, role)
    VALUES ('test_admin', 'admin', 'ADMIN');
INSERT INTO users (uuid, api_key, role)
    VALUES ('test_user', 'user', 'USER');
INSERT INTO tasks (uuid, title, description, project_id, date_due, outcome, author_id)
    VALUES ('test_task01', 'Test task01', 'Task01 description', NULL, '20181205 10:00:00 AM', 'NEW', 'test_admin');
INSERT INTO tasks (uuid, title, description, project_id, date_due, outcome, author_id)
    VALUES ('test_task02', 'Test task01', 'Task01 description', NULL, '20181205 10:00:00 AM', 'NEW', 'test_user');