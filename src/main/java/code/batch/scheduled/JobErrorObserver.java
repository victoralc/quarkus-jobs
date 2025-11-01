package code.batch.scheduled;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.FailedExecution;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ApplicationScoped
public class JobErrorObserver {
    public void observeJobFailure(@Observes FailedExecution exception) {
        ScheduledExecution execution = exception.getExecution();
        Instant fireTime = execution.getFireTime();
        String datetime = LocalDateTime.ofInstant(fireTime, ZoneId.systemDefault()).toString();
        String name = execution.getClass().getName();
        String message = exception.getException().getMessage();

        String text = """
                Job %s failed during execution
                DateTime: %s
                Error message: %s
                """.formatted(name, datetime, message);

        Log.info(text);
    }
}