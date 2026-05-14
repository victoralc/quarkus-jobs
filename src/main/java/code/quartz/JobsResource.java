package code.quartz;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.scheduler.Scheduler;
import io.quarkus.scheduler.Trigger;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("/jobs")
public class JobsResource {

    @Inject
    Scheduler scheduler;

    @GET
    public TemplateInstance renderJobsTemplate() {
        List<Trigger> triggers = scheduler.getScheduledJobs();
        List<ScheduledJob> jobs = new ArrayList<>();
        for (Trigger trigger : triggers) {
            ScheduledJob job = new ScheduledJob();
            job.setName(trigger.getId());
            job.setGroup("quarkus");
            job.setDescription("Scheduled Job " + trigger.getId());
            job.setLastStartTime(trigger.getPreviousFireTime() != null ? trigger.getPreviousFireTime().toString() : "N/A");
            job.setLastRunTime(trigger.getPreviousFireTime() != null ? trigger.getPreviousFireTime().toString() : "N/A");
            job.setNextRunTime(trigger.getNextFireTime() != null ? trigger.getNextFireTime().toString() : "N/A");
            job.setLastRunStatus("N/A");
            job.setActive(trigger.getNextFireTime() != null);
            jobs.add(job);
        }
        return Templates.jobs(jobs);
    }

    private String getStatus(Trigger trigger) {
        if (trigger.getPreviousFireTime() == null && trigger.getNextFireTime() != null) {
            return "Running";
        } else if (trigger.getNextFireTime() != null) {
            return "Scheduled";
        } else {
            return "Completed";
        }
    }

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance jobs(List<ScheduledJob> jobs);

        public static native TemplateInstance jobRow(ScheduledJob job);
    }
}
