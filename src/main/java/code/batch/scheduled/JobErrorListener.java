package code.batch.scheduled;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quartz.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class JobErrorListener implements JobListener {

    @Inject
    Scheduler scheduler;

    private static String getStackTrace(JobExecutionException e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    @Override
    public String getName() {
        return "JobErrorListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        // No change needed here
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        // No change needed here
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException exception) {
        Log.info("JobErrorListener.jobWasExecuted method starting...");
        JobDetail jobDetail = context.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        Instant instant = context.getFireTime().toInstant();
        String datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        try {
            if (exception != null) { //handle error
                String message = exception.getMessage();
                String stackTrace = getStackTrace(exception);

                jobDetail.getJobDataMap().put("LastExecutionTime", datetime);
                jobDetail.getJobDataMap().put("LastFailureMessage", message);
                jobDetail.getJobDataMap().put("LastFailureStackTrace", stackTrace);
                jobDetail.getJobDataMap().put("LastExecutionStatus", "FAILED");

                JobDetail newJobDetail = JobBuilder.newJob(jobDetail.getJobClass())
                        .withIdentity(jobKey)
                        .setJobData(jobDetail.getJobDataMap())
                        .storeDurably()
                        .build();

                scheduler.addJob(newJobDetail, true);

                Log.errorf("Job %s failed at %s. Details stored in JobDataMap.", jobKey.getName(), datetime);

            } else {
                jobDetail.getJobDataMap().put("LastExecutionStatus", "SUCCESS");
                jobDetail.getJobDataMap().put("LastExecutionTime", datetime);
                JobDetail newJobDetail = JobBuilder.newJob(jobDetail.getJobClass())
                        .withIdentity(jobKey)
                        .setJobData(jobDetail.getJobDataMap())
                        .storeDurably()
                        .build();

                scheduler.addJob(newJobDetail, true);
            }
        } catch (SchedulerException se) {
            Log.error("Failed to update JobDetail after execution failure.", se);
        }
    }
}