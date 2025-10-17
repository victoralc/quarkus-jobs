package code.batch;

import code.batch.jobs.JobEntity;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.batch.runtime.JobExecution;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/jobs")
public class JobsResource {

    @Inject
    JobService jobService;

    private static Job mapJob(JobExecution je) {
        return new Job(
                je.getJobName(),
                je.getExecutionId(),
                formatDate(je.getStartTime()),
                formatDate(je.getEndTime()),
                je.getExitStatus());
    }

    public static String formatDate(Date dateToConvert) {
        if (dateToConvert == null) {
            return "";
        }
        Instant instant = dateToConvert.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance renderCustomersTemplate() {
        List<JobEntity> jobs = JobEntity.listAll();
        List<Job> jobData = new ArrayList<>();
        for (JobEntity job : jobs) {
            var result = jobService.getLastJobExecution(job.getName());
            if (result.isEmpty()) {
                jobData.add(Job.empty(job.getName()));
            }else {
                jobData.add(mapJob(result.get()));
            }
        }
        return Templates.jobs(jobData);
    }

    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance start(@FormParam("jobName") String jobName) {
        JobExecution jobExecution = jobService.start(jobName);
        Job job = new Job(jobExecution.getJobName(),
                jobExecution.getExecutionId(),
                formatDate(jobExecution.getStartTime()),
                formatDate(jobExecution.getEndTime()),
                "Started");
        JobStatusSocket.broadcastUpdate(job.name(), Templates.jobRow(job).render());
        return Templates.jobRow(job);
    }

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance jobs(List<Job> jobs);

        public static native TemplateInstance jobRow(Job job);
    }

}
