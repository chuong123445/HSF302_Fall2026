package fu.DE200388;

import fu.DE200388.dao.DepartmentDAO;
import fu.DE200388.pojo.Department;

import fu.DE200388.pojo.Employee;
import fu.DE200388.pojo.Gender;
import fu.DE200388.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {



        public static void main(String[] args) {

            DepartmentDAO departmentDAO = new DepartmentDAO();

            // ==========================================
            // TODO 2.7
            // 1. Tao Department
            // ==========================================
            Department department =
                    new Department("Marketing", "Ha Noi");


            // ==========================================
            // 2. Tao 3 Employee
            // ==========================================
            Employee e1 = new Employee(
                    "Nguyen Van A",
                    "aa.nguyen@company.com",
                    new BigDecimal("15000000"),
                    Gender.MALE,
                    true,
                    LocalDate.of(2022, 1, 10)
            );

            Employee e2 = new Employee(
                    "Tran Thi B",
                    "bb.tran@company.com",
                    new BigDecimal("18000000"),
                    Gender.FEMALE,
                    true,
                    LocalDate.of(2021, 6, 1)
            );

            Employee e3 = new Employee(
                    "Le Van C",
                    "cc.le@company.com",
                    new BigDecimal("12000000"),
                    Gender.OTHER,
                    true,
                    LocalDate.of(2023, 3, 15)
            );


            // ==========================================
            // 3. Them Employee qua helper method
            // ==========================================
            department.addEmployee(e1);
            department.addEmployee(e2);
            department.addEmployee(e3);


            // ==========================================
            // 4. Chi save Department
            // CascadeType.ALL se tu persist 3 Employee
            // ==========================================
            departmentDAO.save(department);

            System.out.println("===== SAVE SUCCESS =====");
            System.out.println(
                    "Department ID: " + department.getId()
            );


            // ==========================================
            // 5. Tim lai bang JOIN FETCH - TODO 2.6
            // ==========================================
            Department found =
                    departmentDAO.findByIdWithEmployees(
                            department.getId()
                    );


            // ==========================================
            // 6. In thong tin Department
            // ==========================================
            System.out.println("\n===== DEPARTMENT =====");

            System.out.println(
                    "ID       : " + found.getId()
            );

            System.out.println(
                    "Name     : " + found.getName()
            );

            System.out.println(
                    "Location : " + found.getLocation()
            );


            // ==========================================
            // 7. In danh sach Employee
            // ==========================================
            System.out.println("\n===== EMPLOYEES =====");

            System.out.println(
                    "Total: " + found.getEmployees().size()
            );

            for (Employee employee : found.getEmployees()) {

                System.out.println("-----------------------");

                System.out.println(
                        "ID       : " + employee.getId()
                );

                System.out.println(
                        "Name     : " + employee.getFullName()
                );

                System.out.println(
                        "Email    : " + employee.getEmail()
                );

                System.out.println(
                        "Gender   : " + employee.getGender()
                );

                System.out.println(
                        "Salary   : " + employee.getSalary()
                );

                System.out.println(
                        "HireDate : " + employee.getHireDate()
                );

                System.out.println(
                        "Active   : " + employee.isActive()
                );
            }


            JPAUtil.close();
        }
}