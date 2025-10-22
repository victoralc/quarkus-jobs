package code.batch;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InjectableBean;
import jakarta.batch.runtime.JobExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jberet.job.model.Job;
import org.jberet.repository.ApplicationAndJobName;
import org.jberet.repository.JobRepository;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@ApplicationScoped
public class JobService {

    @Inject
    QuarkusJobOperator jobOperator;

    public List<JobExecution> getJobExecutions(String name) {
        return jobOperator.getJobExecutionsByJob(name)
                .stream()
                .map(id -> jobOperator.getJobExecution(id))
                .toList();
    }

    public Optional<JobExecution> getLastJobExecution(String name) {
        return getJobExecutions(name).isEmpty() ?
                Optional.empty() :
                Optional.of(getJobExecutions(name).getLast());
    }

    public JobExecution getAllJobExecution(String name) {
        return getJobExecutions(name).getLast();
    }

    public JobExecution start(String jobName) {
        long jobExecutionId = jobOperator.start(jobName, new Properties());
        return jobOperator.getJobExecution(jobExecutionId);
    }
}
