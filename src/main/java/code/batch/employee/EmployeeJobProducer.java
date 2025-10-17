package code.batch.employee;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.Produces;
import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;

@ApplicationScoped
public class EmployeeJobProducer {

    @Produces
    @Named
    public Job employeeJob() {
        return new JobBuilder("employeeJob")
                .step(new StepBuilder("employeeJobStep")
                        .batchlet("updateEmployeeBatchlet")
                        .build())
                .listener("jobEmployeeListener")
                .build();
    }

}
