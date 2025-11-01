package code.batch;

import code.batch.jobs.JobEntity;
import code.batch.util.DateUtil;
import code.batch.util.JobUtil;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.batch.runtime.JobExecution;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.quartz.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/jobs")
public class JobsResource {

    @Inject
    JobService jobService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance renderCustomersTemplate() {
        List<JobEntity> jobs = JobEntity.listAll();
        List<Job> jobData = new ArrayList<>();
        for (JobEntity job : jobs) {
            var result = jobService.getLastJobExecution(job.getName());
            if (result.isEmpty()) {
                jobData.add(Job.empty(job.getName()));
            } else {
                jobData.add(JobUtil.mapJob(result.get()));
            }
        }
        return Templates.jobs(jobData);
    }

    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance start(@FormParam("jobName") String jobName) {
        jobService.start(jobName);
        Job job = new Job(jobName,
                null,
                DateUtil.formatDate(LocalDateTime.now()),
                null,
                "STARTED");
        return Templates.jobRow(job);
    }

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance jobs(List<Job> jobs);

        public static native TemplateInstance jobRow(Job job);
    }

    @Inject
    Scheduler scheduler;

    @GET
    @Path("/scheduled")
    public List<String> getJobDetails() throws SchedulerException {
        List<? extends Trigger> simpleScheduledJob = scheduler.getTriggersOfJob(JobKey.jobKey("SimpleScheduledJob"));
        return List.of();
    }
}
