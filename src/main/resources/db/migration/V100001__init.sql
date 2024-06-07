CREATE SCHEMA IF NOT EXISTS tasks;

CREATE TABLE employees (
    employee_id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE assignment (
    assignment_id UUID PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    employee_id UUID,
    CONSTRAINT fk_employee
            FOREIGN KEY(employee_id)
            REFERENCES tasks.employees(employee_id)
);