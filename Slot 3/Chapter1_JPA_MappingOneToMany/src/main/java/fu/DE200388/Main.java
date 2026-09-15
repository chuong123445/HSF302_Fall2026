package fu.DE200388;

import fu.DE200388.Pojo.Department;

import fu.DE200388.Pojo.Department;
import fu.DE200388.Pojo.Employee;
import fu.DE200388.Pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Department dept = new Department("IT");

        Employee emp = new Employee(
                "Test",
                "test@company.com",
                new BigDecimal("1000"),
                Gender.OTHER,
                true,
                LocalDate.now()
        );

        dept.addEmployee(emp);

        System.out.println(
                dept.getEmployees().contains(emp)
        );

        System.out.println(
                emp.getDepartment() == dept
        );
    }
}