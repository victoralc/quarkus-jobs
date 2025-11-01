package code.batch.scheduled;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SimpleScheduledJob {
    @Transactional
    @Scheduled(every = "10s", identity = "SimpleScheduledJob")
    public void execute() {
        throw new RuntimeException("Error during SimpleScheduledJob execution");
    }
}
