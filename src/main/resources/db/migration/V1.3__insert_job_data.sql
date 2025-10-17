INSERT INTO job (name, description, cron_schedule, created_at) VALUES(
    'employeeJob', 'Update the email of the employee during onboarding time', '0 1 1 0 * *', now());