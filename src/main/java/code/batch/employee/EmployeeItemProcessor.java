package code.batch.employee;

import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;

@Dependent
@Named
public class EmployeeItemProcessor implements ItemProcessor {
    @Override
    public Object processItem(Object item) throws Exception {
        if (item instanceof Employee e) {
            String firstName = e.getFirstName();
            String lastName = e.getLastName();
            String corporateEmail = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
            e.setCorporateEmail(corporateEmail);
            e.setOnboardingStatus(Employee.Status.HR_REVIEWED);
            return e;
        }
        return null;
    }
}
