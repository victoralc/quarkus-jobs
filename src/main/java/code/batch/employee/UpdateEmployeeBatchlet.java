package code.batch.employee;

import io.quarkus.logging.Log;
import jakarta.batch.api.AbstractBatchlet;
import jakarta.batch.runtime.BatchStatus;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.time.Duration;
import java.util.List;

@Dependent
@Named
public class UpdateEmployeeBatchlet extends AbstractBatchlet {

    @Inject
    EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public String process() throws Exception {
        Log.info("throwing exception...");
        throw new RuntimeException();
    }
}
