package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.dto.EnrollmentView;
import com.example.fu.de200388.ch4.dto.StudentCreditDTO;
import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {
    List<Course> getCoursesOfStudent(String studentCode);

    List<Student> getStudentsOfCourse(String courseCode);

    List<Student> findStudentsInCourse(String courseCode);

    long countStudentsInCourse(String courseCode);

    List<Student> findActiveStudentsInCourse(String courseCode);

    List<Student> findStudentsWithoutCourses();

    boolean isEnrolled(String studentCode, String courseCode);

    List<Student> findGoodStudentsInCourse(String courseCode, double minGpa);

    List<StudentCreditDTO> getCreditSummary(int minCredits);

    List<Student> findStudentsWithMoreThan(int n);

    Student getStudentWithCourses(String studentCode);

    List<EnrollmentView> getEnrollmentsOfDepartment(String deptCode);

    Page<Student> findStudentsInCoursePage(String courseCode, int pageIndex, int size);

    List<Student> search(String courseCode, String semester, String deptCode, Double minGpa);

    // Bo sung theo guide - Part E, TODO 20-22 va 24.
    void enroll(String studentCode, String courseCode);

    void unenroll(String studentCode, String courseCode);

    void switchCourse(String studentCode, String fromCode, String toCode);

    int removeEnrollmentsOfInactiveStudents();
}
