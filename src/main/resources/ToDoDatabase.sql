DROP DATABASE IF EXISTS ToDo;
CREATE DATABASE ToDo;
USE ToDo;
CREATE TABLE projects (
    uuid VARCHAR(50) PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(120),
    date_due DATETIME,
    outcome VARCHAR(30));
CREATE TABLE tasks (
    uuid VARCHAR(50) PRIMARY KEY,
    title VARCHAR(30),
    description VARCHAR(120),
    project_id VARCHAR(50),
    date_due DATETIME,
    outcome VARCHAR(30),
    FOREIGN KEY (project_id) REFERENCES projects(uuid));
INSERT INTO tasks (uuid, title, description, project_id, date_due, outcome)
    VALUES ('test_task01', 'Test task01', 'Task01 description', NULL, '20181205 10:00:00 AM', 'NEW');