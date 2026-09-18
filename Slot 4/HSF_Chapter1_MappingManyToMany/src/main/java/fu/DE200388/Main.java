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

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hsf302FU");

        try {
            DepartmentDAO departmentDAO = new DepartmentDAO();
            EmployeeDAO employeeDAO = new EmployeeDAO(emf);
            ProjectDAO projectDAO = new ProjectDAO(emf);

            // ==========================================
            // 1. CREATE DEPARTMENT
            // ==========================================

            Department department =
                    new Department("IT Department", "Ha Noi");

            department.setLocation("Da Nang");


            // ==========================================
            // 2. CREATE 3 EMPLOYEES
            // ==========================================

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


            // ==========================================
            // 3. ASSIGN EMPLOYEES TO DEPARTMENT
            // ==========================================

            department.addEmployee(e1);
            department.addEmployee(e2);
            department.addEmployee(e3);

            /*
             * addEmployee() của Department:
             *
             * employees.add(e);
             * e.setDepartment(this);
             *
             * => Employee.department không còn null.
             */


            // ==========================================
            // 4. SAVE DEPARTMENT
            // ==========================================

            /*
             * Department có:
             *
             * cascade = CascadeType.ALL
             *
             * nên persist Department sẽ persist luôn
             * e1, e2, e3.
             */

            departmentDAO.save(department);


            // ==========================================
            // 5. CREATE 2 PROJECTS
            // ==========================================

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


            // ==========================================
            // 6. SAVE PROJECTS
            // ==========================================

            projectDAO.save(projectA);
            projectDAO.save(projectB);


            // ==========================================
            // TODO 5.7
            // ASSIGN EMPLOYEE <-> PROJECT
            // ==========================================

            // NV1 -> Project A
            employeeDAO.assignEmployeeToProject(
                    e1.getId(),
                    projectA.getId()
            );

            // NV1 -> Project B
            employeeDAO.assignEmployeeToProject(
                    e1.getId(),
                    projectB.getId()
            );

            // NV2 -> Project B
            employeeDAO.assignEmployeeToProject(
                    e2.getId(),
                    projectB.getId()
            );

            // NV3 -> Project A
            employeeDAO.assignEmployeeToProject(
                    e3.getId(),
                    projectA.getId()
            );


            System.out.println("==============================");
            System.out.println("MANY-TO-MANY TEST COMPLETED");
            System.out.println("==============================");

        } finally {
            emf.close();
        }
    }
}