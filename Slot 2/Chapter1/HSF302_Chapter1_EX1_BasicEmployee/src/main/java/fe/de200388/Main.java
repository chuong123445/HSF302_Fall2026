package fe.DE200388;

import fe.DE200388.dao.EmployeeDAO;
import fe.DE200388.pojo.Employee;
import fe.DE200388.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EmployeeDAO dao = new EmployeeDAO();

        // ===== CREATE =====
        Employee emp = new Employee(
                "Nguyen Van A",
                "a@fpt.edu.vn",
                new BigDecimal("15000000"),
                Gender.MALE,
                LocalDate.of(2022, 3, 1)
        );

        dao.save(emp);
        System.out.println("Da tao: " + emp);

        // ===== READ =====
        Employee found = dao.findById(emp.getId());
        System.out.println("Doc lai: " + found);

        // ===== UPDATE =====
        found.setSalary(new BigDecimal("17000000"));

        Employee updated = dao.update(found);
        System.out.println("Sau update: " + updated);

        // ===== READ AGAIN =====
        Employee reChecked = dao.findById(emp.getId());
        System.out.println("Kiem tra lai sau update: " + reChecked);

        // ===== DELETE =====
        dao.delete(emp.getId());
        System.out.println("Da xoa Employee co id: " + emp.getId());

        // ===== READ AFTER DELETE =====
        Employee afterDelete = dao.findById(emp.getId());
        System.out.println("Sau khi xoa, tim lai: " + afterDelete);
    }
}