package code.batch.employee;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class EmployeeDataGenerator {

    private static final int NUM_RECORDS = 80000;
    private static final String FILE_NAME = "import.sql";

    // Departments for variety
    private static final String[] DEPARTMENTS = {"IT", "HR", "Marketing", "Sales", "Finance", "R&D"};

    // Base date for hire dates
    private static final LocalDate BASE_DATE = LocalDate.of(2025, 10, 1);

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            
            // Write the starting comments
            writer.write("-- Auto-generated file with " + NUM_RECORDS + " records for PostgreSQL.\n");
            writer.write("-- All records start with PENDING_ONBOARDING status.\n\n");
            
            // Loop to generate INSERT statements
            for (int i = 1; i <= NUM_RECORDS; i++) {
                long id = 200000 + i; // Start IDs high to avoid conflicts
                String firstName = "BatchUser" + i;
                String lastName = "Test" + (i % 50);
                String personalEmail = "user" + i + "@temp.net";
                
                // Cycle through departments
                String department = DEPARTMENTS[i % DEPARTMENTS.length];
                
                // Stagger hire dates slightly around the base date
                LocalDate hireDate = BASE_DATE.plusDays(i / 100); 

                String sql = String.format(
                    "INSERT INTO Employee (id, firstName, lastName, personalEmail, hireDate, department, onboardingStatus) VALUES (%d, '%s', '%s', '%s', '%s', '%s', 'PENDING_ONBOARDING');\n",
                    id,
                    firstName,
                    lastName,
                    personalEmail,
                    hireDate.toString(),
                    department
                );
                writer.write(sql);
            }

            // Write the sequence reset for PostgreSQL
            long lastId = 200000 + NUM_RECORDS;
            writer.write("\n-- Reset the sequence for subsequent application inserts\n");
            writer.write(String.format("SELECT setval('employee_id_seq', %d, true);\n", lastId));

            System.out.println("✅ Successfully created " + NUM_RECORDS + " records in " + FILE_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}