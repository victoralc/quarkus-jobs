package code.batch.scheduled;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@ApplicationScoped
public class SimpleScheduledJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Log.info("Executing SimpleScheduledJob");
    }
}
