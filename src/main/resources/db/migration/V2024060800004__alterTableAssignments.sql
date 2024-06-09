ALTER TABLE assignments
    ADD COLUMN category_id UUID,
    ADD COLUMN status VARCHAR(20),
    ADD COLUMN priority VARCHAR(20),
    ADD COLUMN created_by_id UUID NOT NULL,
    ADD COLUMN assigned_to_id UUID,
    ADD COLUMN created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    ADD COLUMN assigned_date TIMESTAMP WITH TIME ZONE,
    ADD COLUMN unassigned_date TIMESTAMP WITH TIME ZONE,
    ADD COLUMN done_date TIMESTAMP WITH TIME ZONE;

ALTER TABLE assignments
    ADD CONSTRAINT fk_category
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id);

ALTER TABLE assignments
    ADD CONSTRAINT fk_created_by
        FOREIGN KEY (created_by_id)
        REFERENCES employees(employee_id);

ALTER TABLE assignments
    ADD CONSTRAINT fk_assigned_to
        FOREIGN KEY (assigned_to_id)
        REFERENCES employees(employee_id);

ALTER TABLE assignments
    DROP COLUMN IF EXISTS category;