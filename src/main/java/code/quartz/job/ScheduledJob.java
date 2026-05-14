package code.quartz.job;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "scheduled_job")
public class ScheduledJob {

    public enum Status {
        IDLE,
        RUNNING,
        SCHEDULED,
        PAUSED,
        FAILED,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "job_group")
    private String jobGroup;

    @Column(name = "last_run_time")
    private OffsetDateTime lastRunTime;

    @Column(name = "next_run_time")
    private OffsetDateTime nextRunTime;

    @Column(nullable = false)
    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.IDLE;

    public ScheduledJob() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public OffsetDateTime getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(OffsetDateTime lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public OffsetDateTime getNextRunTime() {
        return nextRunTime;
    }

    public void setNextRunTime(OffsetDateTime nextRunTime) {
        this.nextRunTime = nextRunTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ScheduledJob{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", active=" + active +
                '}';
    }
}
