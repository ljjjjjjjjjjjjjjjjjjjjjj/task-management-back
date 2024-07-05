CREATE TABLE teams (
    team_id UUID PRIMARY KEY,
    team_name VARCHAR(255) UNIQUE NOT NULL,
    team_leader_id UUID NOT NULL,
    FOREIGN KEY (team_leader_id) REFERENCES employees(employee_id)
);


CREATE TABLE team_members (
    team_id UUID,
    employee_id UUID,
    PRIMARY KEY (team_id, employee_id),
    FOREIGN KEY (team_id) REFERENCES teams(team_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

ALTER TABLE employees
  ADD COLUMN team_id UUID,
  ADD CONSTRAINT fk_team
  FOREIGN KEY (team_id) REFERENCES teams(team_id);


CREATE TABLE projects (
    project_id UUID PRIMARY KEY,
    project_name VARCHAR(255) UNIQUE NOT NULL,
    team_id UUID,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    start_date TIMESTAMP WITH TIME ZONE,
    initial_deadline_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE,
    status VARCHAR(50) NOT NULL,
    progress INTEGER NOT NULL CHECK (progress >= 0 AND progress <= 100),
    FOREIGN KEY (team_id) REFERENCES teams(team_id)
);

ALTER TABLE tasks ADD COLUMN project_id UUID REFERENCES projects(project_id);