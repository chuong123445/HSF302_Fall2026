package fu.DE200388.pojo;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String projectCode;

    private String projectName;

    private BigDecimal budget;

    private LocalDate startDate;

    // Có thể null nếu project chưa kết thúc
    private LocalDate endDate;
    // TODO 5.3 - Inverse side
    @ManyToMany(mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();
    public Project() {
    }

    public Project(String projectCode,
                   String projectName,
                   BigDecimal budget,
                   LocalDate startDate,
                   LocalDate endDate) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        Project p= (Project) o;
        return Objects.equals(this.projectCode,((Project) o).getProjectCode());
    }

}