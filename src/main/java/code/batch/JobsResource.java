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
import org.quartz.impl.matchers.GroupMatcher;

import java.time.LocalDateTime;
import java.util.*;

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
    @Path("/details")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, Object>> getFailedJobDetails() throws SchedulerException {
        List<Map<String, Object>> jobs = new ArrayList<>();

        for (String groupName : scheduler.getJobGroupNames()) {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
            for (JobKey jobKey : jobKeys) {
                JobDetail detail = scheduler.getJobDetail(jobKey);
                String status = detail.getJobDataMap().getString("LastExecutionStatus");
                Map<String, Object> jobData = new HashMap<>();
                jobData.put("name", jobKey.getName());
                jobData.put("jobClass", detail.getJobClass().getName());
                jobData.put("status", status);
                jobData.put("time", detail.getJobDataMap().getString("LastExecutionTime"));

                Map<String, String> lastFailure = new HashMap<>();
                lastFailure.put("message", detail.getJobDataMap().getString("LastFailureMessage"));
                lastFailure.put("stackTrace", detail.getJobDataMap().getString("LastFailureStackTrace"));
                jobData.put("lastFailure", lastFailure);

                jobs.add(jobData);
            }
        }
        return jobs;
    }
}
