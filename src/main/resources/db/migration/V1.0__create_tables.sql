
-- 2. Create the employee table
CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    personal_email VARCHAR(255) NOT NULL UNIQUE,
    corporate_email VARCHAR(255) UNIQUE,
    hire_date DATE NOT NULL,
    department VARCHAR(255) NOT NULL,
    onboarding_status VARCHAR(255) NOT NULL DEFAULT 'PENDING_ONBOARDING'
);

CREATE INDEX IF NOT EXISTS idx_employee_personal_email ON employee (personal_email);
CREATE INDEX IF NOT EXISTS idx_employee_corporate_email ON employee (corporate_email);
CREATE INDEX IF NOT EXISTS idx_employee_department ON employee (department);