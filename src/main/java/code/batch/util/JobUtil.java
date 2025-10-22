package code.batch.util;

import code.batch.Job;
import jakarta.batch.runtime.JobExecution;

public class JobUtil {
    public static Job mapJob(JobExecution je) {
        return new Job(
                je.getJobName(),
                je.getExecutionId(),
                DateUtil.formatDate(je.getStartTime()),
                DateUtil.formatDate(je.getEndTime()),
                je.getExitStatus());
    }
}
