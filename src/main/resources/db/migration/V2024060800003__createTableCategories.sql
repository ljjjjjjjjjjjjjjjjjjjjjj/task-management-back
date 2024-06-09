CREATE TABLE categories (
    category_id UUID PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE
);