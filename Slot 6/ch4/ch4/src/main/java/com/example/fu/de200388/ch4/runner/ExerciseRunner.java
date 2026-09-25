package com.example.fu.de200388.ch4.runner;

import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.service.DepartmentService;
import com.example.fu.de200388.ch4.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
}
