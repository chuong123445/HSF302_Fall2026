package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;

import java.util.List;

public interface EnrollmentService {
    List<Course> getCoursesOfStudent(String studentCode);
    List<Student> getStudentsOfCourse(String courseCode);
    List<Student> findStudentsInCourse(String courseCode);
    long countStudentsInCourse(String courseCode);
    List<Student> findActiveStudentsInCourse(String courseCode);
    public List<Student> findStudentsWithoutCourses();
    public boolean isEnrolled(String studentCode,String courseCode);

    }
