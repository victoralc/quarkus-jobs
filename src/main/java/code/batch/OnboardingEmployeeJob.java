package code.batch;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.logging.Log;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;
import org.jberet.job.model.StepBuilder;

import java.util.Properties;

@ApplicationScoped
public class OnboardingEmployeeJob {

    @Inject
    QuarkusJobOperator jobOperator;

    public void start() {
        Job job = new JobBuilder("employeeJob")
                .step(new StepBuilder("updateEmployeeStatus")
                        .batchlet("updateEmployeeBatchlet")
                        .build())
                .build();

        long executionId = jobOperator.start(job, new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);

        Log.info("Executing OnboardingEmployeeJob...");
        BatchStatus batchStatus = jobExecution.getBatchStatus();
        System.out.println("Status " + batchStatus);
    }

}
