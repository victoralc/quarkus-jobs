package code.batch.scheduled;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.quartz.*;

@ApplicationScoped
public class SimpleScheduledJobScheduler {
    private static final SimpleScheduleBuilder EVERY_TEN_SECONDS = SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(10)
            .repeatForever();

    @Inject
    Scheduler quartz;

    void onStart(@Observes StartupEvent event) throws SchedulerException {
        JobDetail job = getSimpleScheduledJob();
        Trigger trigger = getSimpleScheduledTrigger();
        quartz.scheduleJob(job, trigger);
    }

    private static SimpleTrigger getSimpleScheduledTrigger() {
        return TriggerBuilder.newTrigger()
                .withIdentity("SimpleScheduledJobTrigger")
                .withSchedule(EVERY_TEN_SECONDS)
                .build();
    }

    private static JobDetail getSimpleScheduledJob() {
        return JobBuilder.newJob(SimpleScheduledJob.class)
                .withIdentity("SimpleScheduledJob")
                .build();
    }
}
