package code.quartz;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OnboardingEmployeeJob {
    @Scheduled(identity = "OnboardingJob", every = "10s", concurrentExecution = Scheduled.ConcurrentExecution.SKIP)
    public void onboardEmployees() {
        Log.info("Onboarding employees...");
    }
}
