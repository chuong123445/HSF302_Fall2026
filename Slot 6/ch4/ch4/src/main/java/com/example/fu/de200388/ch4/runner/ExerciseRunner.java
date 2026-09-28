package com.example.fu.de200388.ch4.runner;

import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.pojo.Gender;
import com.example.fu.de200388.ch4.service.DepartmentService;
import com.example.fu.de200388.ch4.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
@Order(2)
public class ExerciseRunner implements CommandLineRunner {

    private final DepartmentService departmentService;
    private final StudentService studentService;

    public ExerciseRunner(DepartmentService departmentService, StudentService studentService) {
        this.departmentService = departmentService;
        this.studentService = studentService;
    }

    @Override
    public void run(String... args) {
        runTodo6();
        runTodo7();
        runTodo8();
        runTodo9();
        runTodo10();
        runTodo11();
        runTodo12();
        runTodo13();
        runTodo14();
    }

    private void runTodo6() {
        System.out.println("\n===== TODO 6: Built-in repository methods =====");
        System.out.println("Total departments: " + departmentService.count());
        System.out.println("Total students: " + studentService.count());
        System.out.println("Student id=1: " + studentService.findById(1L)
                .map(Object::toString)
                .orElse("Not found"));
        System.out.println("Student id=99: " + studentService.findById(99L)
                .map(Object::toString)
                .orElse("Not found"));
        System.out.println("Department id=4 exists: " + departmentService.existsById(4L));
    }

    private void runTodo7() {
        System.out.println("\n===== TODO 7: Sort and paginate students =====");

        System.out.println("Students ordered by GPA descending:");
        studentService.findAllOrderByGpaDesc().forEach(System.out::println);

        Page<Student> page = studentService.findPage(1, 3, "fullName");
        System.out.println("Second page ordered by full name ascending:");
        page.getContent().forEach(System.out::println);
        System.out.println("totalElements = " + page.getTotalElements());
        System.out.println("totalPages = " + page.getTotalPages());
        System.out.println("hasNext = " + page.hasNext());
    }

    private void runTodo8() {
        System.out.println("\n===== TODO 8: Derived query by code, email and active status =====");
        System.out.println("Student code AI002: " + studentService.findByStudentCode("AI002")
                .map(Object::toString)
                .orElse("Not found"));
        System.out.println("Student code XX999: " + studentService.findByStudentCode("XX999")
                .map(Object::toString)
                .orElse("Not found"));
        System.out.println("Email binh.tt@fpt.edu.vn exists: "
                + studentService.isEmailExisted("binh.tt@fpt.edu.vn"));
        System.out.println("Active students: " + studentService.countActive());
    }

    private void runTodo9() {
        System.out.println("\n===== TODO 9: Derived query by name and email =====");

        System.out.println("Students whose name contains 'nguyen':");
        studentService.searchByName("nguyen").forEach(System.out::println);

        System.out.println("Students with @gmail.com email:");
        studentService.findByEmailDomain("gmail.com").forEach(System.out::println);

        System.out.println("Students without email:");
        studentService.findWithoutEmail().forEach(System.out::println);
    }

    private void runTodo10() {
        System.out.println("\n===== TODO 10: Derived query by GPA, gender and DOB =====");

        System.out.println("Students with GPA from 3.0 to 3.6:");
        studentService.findByGpaRange(3.0, 3.6).forEach(System.out::println);

        System.out.println("Active male students:");
        studentService.findActiveByGender(Gender.MALE).forEach(System.out::println);

        System.out.println("Students born after 2005-01-01:");
        studentService.findBornAfter(LocalDate.of(2005, 1, 1)).forEach(System.out::println);
    }

    private void runTodo11() {
        System.out.println("\n===== TODO 11: Derived query by department and ranking =====");

        System.out.println("Students in SE ordered by full name:");
        studentService.findByDepartment("SE").forEach(System.out::println);

        System.out.println("Students in AI: " + studentService.countByDepartment("AI"));

        System.out.println("Top 3 students by GPA:");
        studentService.findTop3ByGpa().forEach(System.out::println);

        System.out.println("Departments without students:");
        departmentService.findDepartmentsWithoutStudents().forEach(department ->
                System.out.println(department.getCode() + " - " + department.getName()));
    }

    private void runTodo12() {
        System.out.println("\n===== TODO 12: JPQL with named parameters =====");
        System.out.println("SE students with GPA at least 3.0:");
        studentService.findGoodStudents("SE", 3.0).forEach(System.out::println);
    }

    private void runTodo13() {
        System.out.println("\n===== TODO 13: JPQL keyword search =====");

        System.out.println("Students matching 'hoa':");
        studentService.searchByKeyword("hoa").forEach(System.out::println);

        System.out.println("Students matching 'gmail':");
        studentService.searchByKeyword("gmail").forEach(System.out::println);
    }

    private void runTodo14() {
        System.out.println("\n===== TODO 14: Department statistics =====");
        departmentService.getStatistics().forEach(stat -> {
            String averageGpa = stat.averageGpa() == null
                    ? "null"
                    : String.format(Locale.US, "%.3f", stat.averageGpa());
            System.out.printf(
                    "%s - %s: %d students, average GPA = %s%n",
                    stat.code(),
                    stat.name(),
                    stat.studentCount(),
                    averageGpa
            );
        });
    }
}
