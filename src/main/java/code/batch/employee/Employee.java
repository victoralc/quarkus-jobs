package code.batch.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    public enum Status {
        PENDING_ONBOARDING,
        HR_REVIEWED,
        IT_PROVISIONED,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "corporate_email")
    private String corporateEmail;

    @NotNull(message = "Hire date is required")
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @NotNull(message = "Department is required")
    private String department;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "onboarding_status")
    private Status onboardingStatus = Status.PENDING_ONBOARDING;

    public Employee() {
    }

    // Constructor for initial data loading (before ID is generated)
    public Employee(String firstName, String lastName, String personalEmail, LocalDate hireDate, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personalEmail = personalEmail;
        this.hireDate = hireDate;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getCorporateEmail() {
        return corporateEmail;
    }

    public void setCorporateEmail(String corporateEmail) {
        this.corporateEmail = corporateEmail;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Status getOnboardingStatus() {
        return onboardingStatus;
    }

    public void setOnboardingStatus(Status onboardingStatus) {
        this.onboardingStatus = onboardingStatus;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + firstName + " " + lastName + '\'' +
                ", status=" + onboardingStatus +
                ", corporateEmail='" + corporateEmail + '\'' +
                '}';
    }
}
