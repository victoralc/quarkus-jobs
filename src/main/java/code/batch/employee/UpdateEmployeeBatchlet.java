package code.batch.employee;

import jakarta.batch.api.AbstractBatchlet;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.List;

@Dependent
@Named
public class UpdateEmployeeBatchlet extends AbstractBatchlet {

    @Inject
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public String process() throws Exception {
        Thread.sleep(Duration.ofSeconds(20));
        List<Employee> employees = employeeRepository.listAll();
        employees.forEach(e -> {
            String firstName = e.getFirstName();
            String lastName = e.getLastName();
            String corporateEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
            e.setCorporateEmail(corporateEmail);
            e.setOnboardingStatus(Employee.Status.HR_REVIEWED);
        });
        employeeRepository.persist(employees);
        return "COMPLETED";
    }
}
