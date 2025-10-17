package code.batch.employee;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jberet.job.model.Job;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class Scheduler {

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Inject
    @Named("employeeJob")
    Job employeeJob;

    @Inject
    QuarkusJobOperator jobOperator;

    public void runJob() {
        jobOperator.start(employeeJob, new Properties());
    }

    public void schedule(){
        Log.info("setting up scheduler...");
        executorService.schedule(this::runJob, 5, TimeUnit.SECONDS);
    }

}