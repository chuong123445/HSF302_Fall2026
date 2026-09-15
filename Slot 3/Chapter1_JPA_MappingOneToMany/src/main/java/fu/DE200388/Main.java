package fu.DE200388;

import fu.DE200388.dao.DepartmentDAO;
import fu.DE200388.pojo.Department;

import fu.DE200388.pojo.Employee;
import fu.DE200388.pojo.Gender;
import fu.DE200388.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        // ===== CREATE TEST DATA =====

        Department dept = new Department("IT");

        Employee emp1 = new Employee(
                "Nguyen Van A",
                "a@company.com",
                new BigDecimal("15000000"),
                Gender.MALE,
                true,
                LocalDate.of(2022, 3, 1)
        );

        Employee emp2 = new Employee(
                "Nguyen Thi B",
                "b@company.com",
                new BigDecimal("18000000"),
                Gender.FEMALE,
                true,
                LocalDate.of(2023, 5, 10)
        );

        // Đồng bộ cả 2 phía
        dept.addEmployee(emp1);
        dept.addEmployee(emp2);

        // CascadeType.ALL -> Employee cũng được persist
        departmentDAO.save(dept);

        System.out.println("Department ID: " + dept.getId());


        // ===== TODO 2.6 - JOIN FETCH =====

        Department loaded =
                departmentDAO.findByIdWithEmployees(dept.getId());

        // EntityManager trong DAO đã đóng ở thời điểm này.
        System.out.println("Department: " + loaded.getName());

        System.out.println(
                "Employee count: " + loaded.getEmployees().size()
        );

        for (Employee employee : loaded.getEmployees()) {
            System.out.println(employee.getFullName());
        }


        JPAUtil.close();
    }
}