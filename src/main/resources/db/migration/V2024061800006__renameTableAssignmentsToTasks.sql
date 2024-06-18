ALTER TABLE assignments
    RENAME TO tasks;

ALTER TABLE tasks
    RENAME COLUMN assignment_id TO task_id;