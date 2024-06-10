CREATE TABLE roles (
    role_id INT PRIMARY KEY,
    role_name VARCHAR(20) UNIQUE NOT NULL
);

INSERT INTO roles (role_id, role_name) VALUES (1, 'USER');
INSERT INTO roles (role_id, role_name) VALUES (2, 'ADMIN');

CREATE TABLE employee_roles (
    employee_id UUID NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (employee_id, role_id),
    FOREIGN KEY (employee_id) REFERENCES employees (employee_id),
    FOREIGN KEY (role_id) REFERENCES roles (role_id)
);