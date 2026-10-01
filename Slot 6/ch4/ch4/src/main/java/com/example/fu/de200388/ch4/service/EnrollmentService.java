package com.example.fu.de200388.ch4.service;

import com.example.fu.de200388.ch4.pojo.Course;
import com.example.fu.de200388.ch4.pojo.Student;

import java.util.List;

public interface EnrollmentService {
    List<Course> getCoursesOfStudent(String studentCode);
    List<Student> getStudentsOfCourse(String courseCode);

}
