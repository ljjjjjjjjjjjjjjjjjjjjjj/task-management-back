CREATE TABLE employee_images (
    image_id UUID PRIMARY KEY,
    image_title VARCHAR(255),
    image_data BYTEA NOT NULL,
    employee_id UUID REFERENCES employees(employee_id) ON DELETE CASCADE
);