package com.example.fu.de200388.ch4.runner;

import com.example.fu.de200388.ch4.service.DepartmentService;
import com.example.fu.de200388.ch4.service.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
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
    }
}
