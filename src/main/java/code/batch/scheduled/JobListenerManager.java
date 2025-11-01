package code.batch.scheduled;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

@ApplicationScoped
public class JobListenerManager {

    @Inject
    JobErrorListener jobErrorListener;

    void onStart(@Observes StartupEvent event, Scheduler scheduler) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(jobErrorListener);
    }
}