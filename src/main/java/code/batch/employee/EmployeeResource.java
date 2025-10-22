package code.batch.employee;

import code.batch.OnboardingEmployeeJob;
import io.quarkiverse.jberet.runtime.QuarkusJobOperator;
import jakarta.batch.runtime.JobExecution;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.jberet.job.model.Job;

import java.util.Map;
import java.util.Properties;

@Path("/employees")
public class EmployeeResource {

    @Inject
    EmployeeRepository employeeRepository;

    @Inject
    OnboardingEmployeeJob job;

    @Inject
    @Named("employeeJob")
    Job employeeJob;
    @Inject
    QuarkusJobOperator jobOperator;

    @GET
    @Path("/{id}")
    public Response start(Long id) {
        Employee employee = employeeRepository.findById(id);
        return Response.ok(employee).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        long count = employeeRepository.count();
        return Response.ok(Map.of("totalEmployees", count)).build();
    }

    @GET
    @Path("/jobs/start")
    public String start() {
        job.start();
        return "job executed";
    }

    @GET
    @Path("/job/start")
    public Response startEmployeeJob() {
        long executionId = jobOperator.start(employeeJob, new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        return Response.ok(jobExecution.getBatchStatus()).build();
    }
}
