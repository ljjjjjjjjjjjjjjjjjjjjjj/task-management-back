ALTER TABLE projects
DROP COLUMN team_id;

CREATE TABLE project_teams (
    project_id UUID,
    team_id UUID,
    PRIMARY KEY (project_id, team_id),
    FOREIGN KEY (project_id) REFERENCES projects(project_id),
    FOREIGN KEY (team_id) REFERENCES teams(team_id)
);

CREATE TABLE project_participants (
    project_id UUID,
    employee_id UUID,
    PRIMARY KEY (project_id, employee_id),
    FOREIGN KEY (project_id) REFERENCES projects(project_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);