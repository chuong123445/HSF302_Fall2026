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

        // Lifecycle: emp dang o trang thai NEW / TRANSIENT
        // vi moi duoc tao bang new, chua duoc EntityManager quan ly.
        Employee emp = new Employee(
                "Nguyen Van A",
                "a@fpt.edu.vn",
                new BigDecimal("15000000"),
                Gender.MALE,
                LocalDate.of(2022, 3, 1)
        );

        // Ben trong save():
        // em.persist(emp) lam emp chuyen tu TRANSIENT -> MANAGED.
        dao.save(emp);

        // Sau khi save() return, EntityManager da dong
        // nen emp chuyen sang DETACHED.
        System.out.println("Da tao: " + emp);


        // ===== READ =====

        Employee found = dao.findById(emp.getId());

        // Trong findById(), found tung la MANAGED.
        // Khi findById() return, EntityManager da dong
        // nen found dang o trang thai DETACHED.
        System.out.println("Doc lai: " + found);


        // ===== UPDATE =====

        // found dang DETACHED.
        // Thay doi field luc nay chua tu dong cap nhat DB.
        found.setSalary(new BigDecimal("17000000"));

        // update() goi em.merge(found).
        // Object "found" cu van la DETACHED.
        // merge() tao/tra ve mot object MANAGED trong EntityManager do.
        Employee updated = dao.update(found);

        // Sau khi update() return, EntityManager da dong
        // nen updated cung tro thanh DETACHED.
        System.out.println("Sau update: " + updated);


        // ===== READ AGAIN =====

        Employee reChecked = dao.findById(emp.getId());

        // reChecked tung la MANAGED trong findById(),
        // sau khi method return thi tro thanh DETACHED.
        System.out.println(
                "Kiem tra lai sau update: " + reChecked
        );


        // ===== DELETE =====

        // Ben trong delete():
        // em.find() lay entity -> MANAGED.
        // em.remove() chuyen entity tu MANAGED -> REMOVED.
        // Sau commit, row bi xoa khoi database.
        dao.delete(emp.getId());

        System.out.println(
                "Da xoa Employee co id: " + emp.getId()
        );


        // ===== READ AFTER DELETE =====

        Employee afterDelete = dao.findById(emp.getId());

        // Entity da bi xoa khoi DB,
        // nen findById() tra ve null.
        System.out.println(
                "Sau khi xoa, tim lai: " + afterDelete
        );


        // ===== TODO 0.9: TEST UNIQUE EMAIL =====

        Employee dup1 = new Employee(
                "User 1",
                "trung@fpt.edu.vn",
                new BigDecimal("10000000"),
                Gender.FEMALE,
                LocalDate.now()
        );

        Employee dup2 = new Employee(
                "User 2",
                "trung@fpt.edu.vn",
                new BigDecimal("11000000"),
                Gender.MALE,
                LocalDate.now()
        );

        dao.save(dup1);
        System.out.println(
                "Da tao employee thu nhat: " + dup1
        );

        try {

            dao.save(dup2);

            System.out.println(
                    "LOI: Khong thay exception khi email bi trung!"
            );

        } catch (RuntimeException ex) {

            System.out.println(
                    "Da bat duoc loi trung email nhu ky vong: "
                            + ex.getClass().getSimpleName()
            );
        }
    }
}