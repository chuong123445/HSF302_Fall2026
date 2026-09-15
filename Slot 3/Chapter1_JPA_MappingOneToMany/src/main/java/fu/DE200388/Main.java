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
        // TODO 2.9 - FIX N+1 WITH JOIN FETCH
        // ==========================================

        List<Department> departments =
                departmentDAO.findAllWithEmployees();

        // EntityManager trong DAO da dong
        // nhung employees da duoc JOIN FETCH
        for (Department department : departments) {

            System.out.println(
                    "Department: " + department.getName()
            );

            for (Employee employee : department.getEmployees()) {
                System.out.println(
                        "  - " + employee.getFullName()
                );
            }
        }


        /*
         * TODO 2.8:
         * 1 query load Departments
         * + N queries load Employees
         * => 1 + N queries
         *
         * TODO 2.9:
         * JOIN FETCH Department + Employees
         * => 1 query
         */

        JPAUtil.close();
    }
}