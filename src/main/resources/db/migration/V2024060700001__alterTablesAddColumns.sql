ALTER TABLE employees
                                            RENAME COLUMN name TO first_name;

                                        ALTER TABLE employees
                                            ALTER COLUMN first_name TYPE VARCHAR(64);

                                        ALTER TABLE employees
                                            ADD COLUMN last_name VARCHAR(64) NOT NULL;

                                        ALTER TABLE employees
                                            ALTER COLUMN email TYPE VARCHAR(128),
                                            ALTER COLUMN password TYPE VARCHAR(64);

                                        ALTER TABLE assignment
                                            ALTER COLUMN title TYPE VARCHAR(128);

                                        ALTER TABLE assignment
                                            ADD COLUMN category VARCHAR(128) NOT NULL,
                                            ADD COLUMN description VARCHAR(512);