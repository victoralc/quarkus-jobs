package code.batch.employee;

import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import io.quarkus.logging.Log;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.batch.api.listener.AbstractJobListener;
import jakarta.batch.runtime.BatchStatus;
import jakarta.batch.runtime.JobExecution;
import jakarta.batch.runtime.context.JobContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Named
@Dependent
public class JobEmployeeListener extends AbstractJobListener {

    @Inject
    private JobContext jobContext;

    @Inject
    QuarkusJobOperator quarkusJobOperator;

    @Inject
    Mailer mailer;

    @Override
    public void afterJob() throws Exception {
        Log.info("executing afterJob...");
        long executionId = jobContext.getExecutionId();
        JobExecution jobExecution = quarkusJobOperator.getJobExecution(executionId);
        if (jobExecution.getBatchStatus().equals(BatchStatus.FAILED)) {
            mailer.send(Mail.withText(
                    "victoralcantara432@gmail.com",
                    "Message test",
                    "Quarkus message job failed"));
        }
    }

    public static String formatDate(Date dateToConvert) {
        Instant instant = dateToConvert.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}
