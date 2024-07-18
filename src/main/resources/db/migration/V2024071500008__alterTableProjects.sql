ALTER TABLE projects
  ADD COLUMN created_by_id UUID;

ALTER TABLE projects
  ADD CONSTRAINT fk_created_by
  FOREIGN KEY (created_by_id) REFERENCES employees(employee_id);


