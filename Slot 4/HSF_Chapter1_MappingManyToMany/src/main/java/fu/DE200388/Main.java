package fu.DE200388;

import fu.DE200388.dao.DepartmentDAO;
import fu.DE200388.dao.EmployeeDAO;
import fu.DE200388.dao.ProjectDAO;
import fu.DE200388.pojo.Department;
import fu.DE200388.pojo.Employee;
import fu.DE200388.pojo.Gender;
import fu.DE200388.pojo.Project;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hsf302FU");

        try {

            DepartmentDAO departmentDAO = new DepartmentDAO();
            EmployeeDAO employeeDAO = new EmployeeDAO(emf);
            ProjectDAO projectDAO = new ProjectDAO(emf);


            // =====================================================
            // TODO 5.7 - CREATE TEST DATA
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("TODO 5.7 - CREATE MANY-TO-MANY DATA");
            System.out.println("========================================");


            // Employee vẫn yêu cầu Department từ bài OneToMany
            Department department =
                    new Department("IT Department", "Ha Noi");

            department.setLocation("Da Nang");


            // ----- Employees -----

            Employee e1 = new Employee(
                    "Nguyen Van A",
                    "nva@gmail.com",
                    new BigDecimal("15000000"),
                    Gender.MALE,
                    true,
                    LocalDate.of(2024, 1, 10)
            );

            Employee e2 = new Employee(
                    "Nguyen Van B",
                    "nvb@gmail.com",
                    new BigDecimal("17000000"),
                    Gender.MALE,
                    true,
                    LocalDate.of(2024, 2, 15)
            );

            Employee e3 = new Employee(
                    "Nguyen Thi C",
                    "ntc@gmail.com",
                    new BigDecimal("18000000"),
                    Gender.FEMALE,
                    true,
                    LocalDate.of(2024, 3, 20)
            );


            // Gán Department
            department.addEmployee(e1);
            department.addEmployee(e2);
            department.addEmployee(e3);


            // Cascade ALL -> persist luôn Employees
            departmentDAO.save(department);


            // ----- Projects -----

            Project projectA = new Project(
                    "P001",
                    "Project A",
                    new BigDecimal("500000000"),
                    LocalDate.of(2026, 1, 1),
                    null
            );

            Project projectB = new Project(
                    "P002",
                    "Project B",
                    new BigDecimal("800000000"),
                    LocalDate.of(2026, 3, 1),
                    null
            );


            projectDAO.save(projectA);
            projectDAO.save(projectB);


            // ----- ManyToMany -----

            // NV1 -> A + B
            employeeDAO.assignEmployeeToProject(
                    e1.getId(),
                    projectA.getId()
            );

            employeeDAO.assignEmployeeToProject(
                    e1.getId(),
                    projectB.getId()
            );


            // NV2 -> B
            employeeDAO.assignEmployeeToProject(
                    e2.getId(),
                    projectB.getId()
            );


            // NV3 -> A
            employeeDAO.assignEmployeeToProject(
                    e3.getId(),
                    projectA.getId()
            );


            System.out.println("Created:");
            System.out.println("NV1 -> Project A, Project B");
            System.out.println("NV2 -> Project B");
            System.out.println("NV3 -> Project A");


            // =====================================================
            // TODO 5.8
            // COUNT ACTIVE EMPLOYEES + SUM SALARY PER PROJECT
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("TODO 5.8 - PROJECT STATISTICS");
            System.out.println("========================================");

            projectDAO.getProjectEmployeeStatistics();


            // =====================================================
            // TODO 5.10
            // ACTIVE EMPLOYEES WITH MORE THAN 1 PROJECT
            //
            // Chạy trước 5.9 vì hiện tại NV1 có 2 projects
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("TODO 5.10 - EMPLOYEES WITH > 1 PROJECT");
            System.out.println("========================================");


            List<Employee> multipleProjectEmployees =
                    employeeDAO.findActiveEmployeesWithMultipleProjects();


            if (multipleProjectEmployees.isEmpty()) {

                System.out.println("No employee found.");

            } else {

                for (Employee employee : multipleProjectEmployees) {

                    System.out.println(
                            "Employee: "
                                    + employee.getFullName()
                                    + " | Email: "
                                    + employee.getEmail()
                    );
                }
            }


            // =====================================================
            // TODO 5.9
            // UNASSIGN NV1 FROM PROJECT A
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("TODO 5.9 - UNASSIGN PROJECT");
            System.out.println("========================================");

            System.out.println(
                    "Before: Nguyen Van A -> Project A, Project B"
            );


            employeeDAO.unassignEmployeeFromProject(
                    e1.getId(),
                    projectA.getId()
            );


            System.out.println(
                    "Removed: Nguyen Van A -> Project A"
            );

            System.out.println(
                    "After: Nguyen Van A -> Project B"
            );

            System.out.println(
                    "Check employee_project: exactly 1 row removed."
            );


            // =====================================================
            // RUN 5.8 AGAIN AFTER UNASSIGN
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("PROJECT STATISTICS AFTER UNASSIGN");
            System.out.println("========================================");

            projectDAO.getProjectEmployeeStatistics();


            // =====================================================
            // RUN 5.10 AGAIN
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("TODO 5.10 - AFTER UNASSIGN");
            System.out.println("========================================");


            multipleProjectEmployees =
                    employeeDAO.findActiveEmployeesWithMultipleProjects();


            if (multipleProjectEmployees.isEmpty()) {

                System.out.println(
                        "No active employee participates in more than 1 project."
                );

            } else {

                for (Employee employee : multipleProjectEmployees) {

                    System.out.println(
                            employee.getFullName()
                    );
                }
            }


            // =====================================================
            // TODO 5.11
            // DEACTIVATE EMPLOYEE
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("TODO 5.11 - DEACTIVATE EMPLOYEE");
            System.out.println("========================================");


            System.out.println(
                    "Deactivate employee: " + e2.getFullName()
            );


            employeeDAO.deactivateEmployee(
                    e2.getId()
            );


            System.out.println(
                    "Employee ID "
                            + e2.getId()
                            + " is now inactive."
            );


            // =====================================================
            // RUN 5.8 AGAIN
            // inactive employee sẽ không được tính
            // =====================================================

            System.out.println("\n========================================");
            System.out.println("PROJECT STATISTICS AFTER DEACTIVATE");
            System.out.println("========================================");

            projectDAO.getProjectEmployeeStatistics();


            System.out.println("\n========================================");
            System.out.println("ALL MANY-TO-MANY TESTS COMPLETED");
            System.out.println("========================================");


        } finally {

            emf.close();
        }
    }
}