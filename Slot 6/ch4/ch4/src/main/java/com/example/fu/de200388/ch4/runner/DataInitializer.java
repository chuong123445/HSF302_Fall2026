package com.example.fu.de200388.ch4.runner;

import com.example.fu.de200388.ch4.pojo.Department;
import com.example.fu.de200388.ch4.pojo.Gender;
import com.example.fu.de200388.ch4.pojo.Student;
import com.example.fu.de200388.ch4.repository.DepartmentRepository;
import com.example.fu.de200388.ch4.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    public DataInitializer(
            DepartmentRepository departmentRepository,
            StudentRepository studentRepository
    ) {
        this.departmentRepository = departmentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        Department se = new Department("SE", "Software Engineering");
        Department ai = new Department("AI", "Artificial Intelligence");
        Department ia = new Department("IA", "Information Assurance");
        Department gd = new Department("GD", "Graphic Design");

        departmentRepository.saveAll(List.of(se, ai, ia, gd));

        List<Student> students = List.of(
                createStudent(se, "SE001", "Nguyen Van An", "an.nv@fpt.edu.vn",
                        Gender.MALE, LocalDate.of(2005, 3, 15), 3.2, true),
                createStudent(se, "SE002", "Tran Thi Binh", "binh.tt@fpt.edu.vn",
                        Gender.FEMALE, LocalDate.of(2004, 7, 22), 3.8, true),
                createStudent(se, "SE003", "Le Van Cuong", "cuong.lv@fpt.edu.vn",
                        Gender.MALE, LocalDate.of(2003, 11, 5), 2.5, false),
                createStudent(ai, "AI001", "Pham Thi Dung", "dung.pt@fpt.edu.vn",
                        Gender.FEMALE, LocalDate.of(2006, 1, 10), 3.5, true),
                createStudent(ai, "AI002", "Hoang Van Em", "em.hv@gmail.com",
                        Gender.MALE, LocalDate.of(2002, 9, 30), 2.8, true),
                createStudent(ai, "AI003", "Vo Thi Hoa", "hoa.vt@fpt.edu.vn",
                        Gender.FEMALE, LocalDate.of(2005, 5, 18), 3.9, true),
                createStudent(ia, "IA001", "Dang Van Giang", "giang.dv@gmail.com",
                        Gender.MALE, LocalDate.of(2001, 12, 1), 1.9, false),
                createStudent(ia, "IA002", "Bui Thi Lan", "lan.bt@fpt.edu.vn",
                        Gender.FEMALE, LocalDate.of(2004, 2, 14), 3.1, true),
                createStudent(se, "SE004", "Nguyen Thi Mai", "mai.nt@fpt.edu.vn",
                        Gender.FEMALE, LocalDate.of(2003, 8, 8), 3.6, true),
                createStudent(ia, "IA003", "Do Van Nam", null,
                        Gender.MALE, LocalDate.of(2005, 10, 20), 2.2, true)
        );

        studentRepository.saveAll(students);
    }

    private Student createStudent(
            Department department,
            String studentCode,
            String fullName,
            String email,
            Gender gender,
            LocalDate dob,
            double gpa,
            boolean active
    ) {
        Student student = new Student(studentCode, fullName, email, gender, dob, gpa, active);
        department.addStudent(student);
        return student;
    }
}
