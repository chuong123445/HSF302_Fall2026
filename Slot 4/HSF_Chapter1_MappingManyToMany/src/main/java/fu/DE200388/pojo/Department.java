package fu.DE200388.pojo;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String location;
    // TODO 2.3 - Inverse side
    @OneToMany(
            mappedBy = "department",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Employee> employees = new ArrayList<>();


    // No-arg constructor
    public Department() {
    }

    // Convenience constructor
    public Department(String name,String location) {
        this.name = name;
        this.location=location;
    }
    // TODO 2.4 - Helper methods

    public void addEmployee(Employee e) {
        this.employees.add(e);
        e.setDepartment(this);
    }

    public void removeEmployee(Employee e) {
        this.employees.remove(e);
        e.setDepartment(null);
    }


    // Getter / Setter

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
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}