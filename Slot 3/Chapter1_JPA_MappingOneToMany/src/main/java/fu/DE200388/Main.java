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

        // =========================================
        // Tao du lieu test
        // =========================================

        Department it = new Department("IT");

        it.addEmployee(new Employee(
                "Nguyen Van A",
                "it.a@company.com",
                new BigDecimal("15000000"),
                Gender.MALE,
                true,
                LocalDate.of(2022, 1, 10)
        ));

        Department marketing = new Department("Marketing");

        marketing.addEmployee(new Employee(
                "Tran Thi B",
                "marketing.b@company.com",
                new BigDecimal("18000000"),
                Gender.FEMALE,
                true,
                LocalDate.of(2021, 6, 1)
        ));

        Department hr = new Department("HR");

        hr.addEmployee(new Employee(
                "Le Van C",
                "hr.c@company.com",
                new BigDecimal("12000000"),
                Gender.OTHER,
                true,
                LocalDate.of(2023, 3, 15)
        ));

        departmentDAO.save(it);
        departmentDAO.save(marketing);
        departmentDAO.save(hr);


        // =========================================
        // TODO 2.8 - N+1 QUERY PROBLEM
        // =========================================

        EntityManager em =
                JPAUtil.getEMF().createEntityManager();

        try {

            // Query 1: Lay tat ca Department
            List<Department> departments =
                    em.createQuery(
                            "SELECT d FROM Department d",
                            Department.class
                    ).getResultList();

            // Moi Department se phat sinh them
            // 1 query khi truy cap employees lan dau
            for (Department department : departments) {

                System.out.println(
                        department.getName()
                                + " - Employees: "
                                + department.getEmployees().size()
                );
            }

        } finally {
            em.close();
        }

        JPAUtil.close();
    }
}
