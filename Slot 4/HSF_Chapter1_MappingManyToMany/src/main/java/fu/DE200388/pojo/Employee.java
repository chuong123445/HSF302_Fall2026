package fu.DE200388.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean active;

    private LocalDate hireDate;

    // TODO 2.2 - Owning side
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;


    // No-arg constructor
    public Employee() {
    }

    // Convenience constructor
    public Employee(String fullName,
                    String email,
                    BigDecimal salary,
                    Gender gender,
                    boolean active,
                    LocalDate hireDate) {
        this.fullName = fullName;
        this.email = email;
        this.salary = salary;
        this.gender = gender;
        this.active = active;
        this.hireDate = hireDate;
    }


    // Getter / Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}